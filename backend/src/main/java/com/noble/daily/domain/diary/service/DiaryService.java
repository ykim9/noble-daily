package com.noble.daily.domain.diary.service;

import com.noble.daily.domain.diary.*;
import com.noble.daily.domain.user.User;
import com.noble.daily.external.claude.ClaudeApiClient;
import com.noble.daily.external.claude.dto.DiaryConversionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 일기 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DiaryService {
    
    private final DiaryRepository diaryRepository;
    private final ClaudeApiClient claudeApiClient;
    
    /**
     * 일기 작성 (Claude API 호출 포함)
     */
    @Transactional
    public Diary createDiary(User user, String originalText, Persona persona) {
        log.info("일기 작성 시작 - User: {}, Persona: {}", user.getId(), persona);
        
        // Claude API 호출 (변환 + 감정 분석)
        DiaryConversionResult result = claudeApiClient.convertDiary(originalText, persona);
        
        // 감정 분석 객체 생성
        EmotionAnalysis emotions = EmotionAnalysis.of(
                result.getEmotions().getAnger(),
                result.getEmotions().getSadness(),
                result.getEmotions().getJoy(),
                result.getEmotions().getAnxiety(),
                result.getEmotions().getFatigue(),
                result.getComment()
        );
        
        // 일기 엔티티 생성 및 저장
        Diary diary = Diary.create(user, originalText, persona, result.getConvertedText(), emotions);
        Diary savedDiary = diaryRepository.save(diary);
        
        log.info("일기 작성 완료 - Diary ID: {}", savedDiary.getId());
        return savedDiary;
    }
    
    /**
     * 사용자의 일기 목록 조회
     */
    public List<Diary> getUserDiaries(Long userId) {
        return diaryRepository.findByUserIdWithUser(userId);
    }
    
    /**
     * 일기 단건 조회
     */
    public Diary getDiary(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다. ID: " + diaryId));
    }
    
    /**
     * 일기 수정
     */
    @Transactional
    public Diary updateDiary(Long diaryId, String originalText, Persona persona) {
        Diary diary = getDiary(diaryId);
        
        // Claude API 호출
        DiaryConversionResult result = claudeApiClient.convertDiary(originalText, persona);
        
        // 감정 분석 객체 생성
        EmotionAnalysis emotions = EmotionAnalysis.of(
                result.getEmotions().getAnger(),
                result.getEmotions().getSadness(),
                result.getEmotions().getJoy(),
                result.getEmotions().getAnxiety(),
                result.getEmotions().getFatigue(),
                result.getComment()
        );
        
        // 일기 업데이트
        diary.update(originalText, persona, result.getConvertedText(), emotions);
        
        log.info("일기 수정 완료 - Diary ID: {}", diaryId);
        return diary;
    }
    
    /**
     * 일기 삭제
     */
    @Transactional
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
        log.info("일기 삭제 완료 - Diary ID: {}", diaryId);
    }
}
