package com.noble.daily.domain.diary;

import com.noble.daily.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 일기 엔티티
 */
@Entity
@Table(name = "diaries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "original_text", nullable = false, columnDefinition = "TEXT")
    private String originalText;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Persona persona;
    
    @Column(name = "converted_text", nullable = false, columnDefinition = "TEXT")
    private String convertedText;
    
    @Embedded
    private EmotionAnalysis emotions;
    
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * 새로운 일기 생성
     */
    public static Diary create(User user, String originalText, Persona persona,
                               String convertedText, EmotionAnalysis emotions) {
        Diary diary = new Diary();
        diary.user = user;
        diary.originalText = originalText;
        diary.persona = persona;
        diary.convertedText = convertedText;
        diary.emotions = emotions;
        return diary;
    }
    
    /**
     * 일기 수정
     */
    public void update(String originalText, Persona persona,
                      String convertedText, EmotionAnalysis emotions) {
        this.originalText = originalText;
        this.persona = persona;
        this.convertedText = convertedText;
        this.emotions = emotions;
    }
}
