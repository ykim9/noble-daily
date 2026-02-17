package com.noble.daily.external.claude;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noble.daily.config.AnthropicProperties;
import com.noble.daily.domain.diary.Persona;
import com.noble.daily.external.claude.dto.ClaudeRequest;
import com.noble.daily.external.claude.dto.ClaudeResponse;
import com.noble.daily.external.claude.dto.DiaryConversionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * Claude API 클라이언트
 * 일기 변환 + 감정 분석을 1번의 API 호출로 통합
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ClaudeApiClient {
    
    private final WebClient webClient;
    private final AnthropicProperties properties;
    private final ObjectMapper objectMapper;
    
    /**
     * 일기를 페르소나 스타일로 변환하고 감정 분석 수행
     */
    public DiaryConversionResult convertDiary(String originalText, Persona persona) {
        String systemPrompt = buildSystemPrompt(persona);
        String userMessage = buildUserMessage(originalText);
        
        log.debug("Claude API 요청 - Persona: {}, Text length: {}", persona, originalText.length());
        
        ClaudeRequest request = ClaudeRequest.of(properties.getModel(), systemPrompt, userMessage);
        
        ClaudeResponse response = webClient.post()
                .uri(properties.getApiUrl())
                .header("x-api-key", properties.getApiKey())
                .header("anthropic-version", "2023-06-01")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ClaudeResponse.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .maxBackoff(Duration.ofSeconds(5)))
                .doOnError(error -> log.error("Claude API 호출 실패", error))
                .block();
        
        if (response == null) {
            throw new RuntimeException("Claude API 응답이 null입니다.");
        }
        
        String responseText = response.getTextContent();
        log.debug("Claude API 응답 수신: {} characters", responseText.length());
        
        return parseResponse(responseText);
    }
    
    /**
     * System Prompt 구성
     */
    private String buildSystemPrompt(Persona persona) {
        return persona.getSystemPrompt() + """
                
                변환 후 반드시 아래 JSON 형식으로 응답하세요:
                {
                  "converted_text": "변환된 일기 전문",
                  "emotions": {
                    "anger": 0-100,
                    "sadness": 0-100,
                    "joy": 0-100,
                    "anxiety": 0-100,
                    "fatigue": 0-100
                  },
                  "comment": "페르소나 스타일의 감정 코멘트 (1-2줄)"
                }
                
                주의사항:
                - 불법/유해 콘텐츠는 변환 거부
                - JSON 형식을 정확히 지킬 것
                - 감정 수치는 0-100 사이 정수
                """;
    }
    
    /**
     * User Message 구성
     */
    private String buildUserMessage(String originalText) {
        return String.format("""
                아래 일기를 변환하고 감정을 분석해주세요.
                
                일기:
                %s
                """, originalText);
    }
    
    /**
     * Claude 응답을 DiaryConversionResult로 파싱
     */
    private DiaryConversionResult parseResponse(String responseText) {
        // JSON 추출 (```json ``` 제거)
        String jsonText = responseText.trim();
        if (jsonText.startsWith("```json")) {
            jsonText = jsonText.substring(7);
        }
        if (jsonText.startsWith("```")) {
            jsonText = jsonText.substring(3);
        }
        if (jsonText.endsWith("```")) {
            jsonText = jsonText.substring(0, jsonText.length() - 3);
        }
        jsonText = jsonText.trim();
        
        try {
            return objectMapper.readValue(jsonText, DiaryConversionResult.class);
        } catch (JsonProcessingException e) {
            log.error("Claude 응답 파싱 실패: {}", jsonText, e);
            throw new RuntimeException("Claude API 응답 파싱에 실패했습니다.", e);
        }
    }
}
