package com.noble.daily.api.diary;

import com.noble.daily.api.diary.dto.DiaryCreateRequest;
import com.noble.daily.api.diary.dto.DiaryResponse;
import com.noble.daily.domain.diary.Diary;
import com.noble.daily.domain.diary.service.DiaryService;
import com.noble.daily.domain.user.User;
import com.noble.daily.domain.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 일기 API 컨트롤러
 */
@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {
    
    private final DiaryService diaryService;
    private final UserRepository userRepository;
    
    /**
     * 일기 작성
     */
    @PostMapping
    public ResponseEntity<DiaryResponse> createDiary(@Valid @RequestBody DiaryCreateRequest request) {
        log.info("일기 작성 요청 - Persona: {}", request.getPersona());
        
        // TODO: 실제로는 Spring Security에서 인증된 사용자 가져오기
        // 현재는 임시로 첫 번째 사용자 사용 (테스트용)
        User user = userRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    // 테스트용 사용자 자동 생성
                    User testUser = User.createOAuthUser(
                            "test", 
                            "test-user-1", 
                            "test@example.com", 
                            "테스트 사용자", 
                            null
                    );
                    return userRepository.save(testUser);
                });
        
        Diary diary = diaryService.createDiary(user, request.getOriginalText(), request.getPersona());
        return ResponseEntity.ok(DiaryResponse.from(diary));
    }
    
    /**
     * 일기 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<DiaryResponse>> getDiaries() {
        // TODO: 인증된 사용자의 일기만 조회
        User user = userRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("사용자가 없습니다."));
        
        List<Diary> diaries = diaryService.getUserDiaries(user.getId());
        List<DiaryResponse> responses = diaries.stream()
                .map(DiaryResponse::from)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    /**
     * 일기 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponse> getDiary(@PathVariable Long id) {
        Diary diary = diaryService.getDiary(id);
        return ResponseEntity.ok(DiaryResponse.from(diary));
    }
    
    /**
     * 일기 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiaryResponse> updateDiary(
            @PathVariable Long id,
            @Valid @RequestBody DiaryCreateRequest request) {
        Diary diary = diaryService.updateDiary(id, request.getOriginalText(), request.getPersona());
        return ResponseEntity.ok(DiaryResponse.from(diary));
    }
    
    /**
     * 일기 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
        return ResponseEntity.noContent().build();
    }
}
