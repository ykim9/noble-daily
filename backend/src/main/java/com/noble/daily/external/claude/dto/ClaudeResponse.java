package com.noble.daily.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Claude API 응답 DTO
 */
@Getter
@NoArgsConstructor
public class ClaudeResponse {
    
    private String id;
    private String type;
    private String role;
    private List<Content> content;
    private String model;
    
    @JsonProperty("stop_reason")
    private String stopReason;
    
    @Getter
    @NoArgsConstructor
    public static class Content {
        private String type;
        private String text;
    }
    
    /**
     * 응답 텍스트 추출
     */
    public String getTextContent() {
        if (content == null || content.isEmpty()) {
            return "";
        }
        return content.get(0).getText();
    }
}
