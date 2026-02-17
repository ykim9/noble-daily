package com.noble.daily.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * OAuth Provider와 OAuth ID로 사용자 조회
     */
    Optional<User> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);
    
    /**
     * 이메일로 사용자 조회
     */
    Optional<User> findByEmail(String email);
}
