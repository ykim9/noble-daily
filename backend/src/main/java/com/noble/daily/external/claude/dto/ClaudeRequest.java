package com.noble.daily.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Claude API 요청 DTO
 */
@Getter
@AllArgsConstructor
public class ClaudeRequest {
    
    private String model;
    
    @JsonProperty("max_tokens")
    private int maxTokens;
    
    private List<Message> messages;
    
    private String system;
    
    @Getter
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
    
    public static ClaudeRequest of(String model, String systemPrompt, String userMessage) {
        return new ClaudeRequest(
                model,
                2000,
                List.of(new Message("user", userMessage)),
                systemPrompt
        );
    }
}
