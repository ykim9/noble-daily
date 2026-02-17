package com.noble.daily.api.diary.dto;

import com.noble.daily.domain.diary.Diary;
import com.noble.daily.domain.diary.EmotionAnalysis;
import com.noble.daily.domain.diary.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 일기 응답 DTO
 */
@Getter
@AllArgsConstructor
public class DiaryResponse {
    
    private Long id;
    private String originalText;
    private Persona persona;
    private String convertedText;
    private EmotionDto emotions;
    private String aiComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Getter
    @AllArgsConstructor
    public static class EmotionDto {
        private Integer anger;
        private Integer sadness;
        private Integer joy;
        private Integer anxiety;
        private Integer fatigue;
        
        public static EmotionDto from(EmotionAnalysis emotions) {
            if (emotions == null) {
                return null;
            }
            return new EmotionDto(
                    emotions.getAnger(),
                    emotions.getSadness(),
                    emotions.getJoy(),
                    emotions.getAnxiety(),
                    emotions.getFatigue()
            );
        }
    }
    
    public static DiaryResponse from(Diary diary) {
        return new DiaryResponse(
                diary.getId(),
                diary.getOriginalText(),
                diary.getPersona(),
                diary.getConvertedText(),
                EmotionDto.from(diary.getEmotions()),
                diary.getEmotions() != null ? diary.getEmotions().getAiComment() : null,
                diary.getCreatedAt(),
                diary.getUpdatedAt()
        );
    }
}
