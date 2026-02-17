package com.noble.daily.domain.diary;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 감정 분석 결과 (임베디드 타입)
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmotionAnalysis {
    
    @Column(name = "emotion_anger", columnDefinition = "TINYINT")
    private Integer anger;      // 분노 (0-100)
    
    @Column(name = "emotion_sadness", columnDefinition = "TINYINT")
    private Integer sadness;    // 우울 (0-100)
    
    @Column(name = "emotion_joy", columnDefinition = "TINYINT")
    private Integer joy;        // 기쁨 (0-100)
    
    @Column(name = "emotion_anxiety", columnDefinition = "TINYINT")
    private Integer anxiety;    // 불안 (0-100)
    
    @Column(name = "emotion_fatigue", columnDefinition = "TINYINT")
    private Integer fatigue;    // 피로 (0-100)
    
    @Column(name = "ai_comment", columnDefinition = "TEXT")
    private String aiComment;   // AI의 페르소나 스타일 코멘트
    
    /**
     * 감정 분석 결과 생성
     */
    public static EmotionAnalysis of(Integer anger, Integer sadness, Integer joy, 
                                     Integer anxiety, Integer fatigue, String aiComment) {
        return new EmotionAnalysis(anger, sadness, joy, anxiety, fatigue, aiComment);
    }
}
