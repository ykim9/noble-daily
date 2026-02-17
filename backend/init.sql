-- Noble Daily Database Schema
-- DATETIME(6) 사용: 2038년 문제 없음, 마이크로초 정밀도

CREATE TABLE users (
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    oauth_provider VARCHAR(20)  NOT NULL COMMENT 'google, kakao',
    oauth_id       VARCHAR(100) NOT NULL COMMENT 'OAuth provider user id',
    email          VARCHAR(100),
    name           VARCHAR(50),
    profile_image  VARCHAR(255),
    created_at     DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    UNIQUE KEY unique_oauth (oauth_provider, oauth_id),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE diaries (
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id         BIGINT       NOT NULL,
    original_text   TEXT         NOT NULL COMMENT '원본 일기 (최대 500자)',
    persona         VARCHAR(50)  NOT NULL COMMENT 'JOSEON, BRIDGERTON, CHINESE, FANTASY',
    converted_text  TEXT         NOT NULL COMMENT '변환된 일기',
    
    -- 감정 분석 (emotions 테이블 통합)
    emotion_anger   TINYINT COMMENT '분노 (0-100)',
    emotion_sadness TINYINT COMMENT '우울 (0-100)',
    emotion_joy     TINYINT COMMENT '기쁨 (0-100)',
    emotion_anxiety TINYINT COMMENT '불안 (0-100)',
    emotion_fatigue TINYINT COMMENT '피로 (0-100)',
    ai_comment      TEXT    COMMENT 'AI의 페르소나 스타일 코멘트',
    
    created_at      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at      DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_created (user_id, created_at DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
