package com.noble.daily.external.claude.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일기 변환 결과 (Claude API 응답 파싱용)
 */
@Getter
@NoArgsConstructor
public class DiaryConversionResult {
    
    @JsonProperty("converted_text")
    private String convertedText;
    
    private Emotions emotions;
    
    private String comment;
    
    @Getter
    @NoArgsConstructor
    public static class Emotions {
        private Integer anger;
        private Integer sadness;
        private Integer joy;
        private Integer anxiety;
        private Integer fatigue;
    }
}
