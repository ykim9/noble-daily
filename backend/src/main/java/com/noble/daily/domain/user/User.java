package com.noble.daily.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사용자 엔티티 (OAuth 인증)
 */
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "oauth_provider", nullable = false, length = 20)
    private String oauthProvider;  // google, kakao
    
    @Column(name = "oauth_id", nullable = false, length = 100)
    private String oauthId;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 50)
    private String name;
    
    @Column(name = "profile_image")
    private String profileImage;
    
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
    /**
     * 새로운 사용자 생성 (OAuth)
     */
    public static User createOAuthUser(String provider, String oauthId, 
                                       String email, String name, String profileImage) {
        User user = new User();
        user.oauthProvider = provider;
        user.oauthId = oauthId;
        user.email = email;
        user.name = name;
        user.profileImage = profileImage;
        return user;
    }
}
