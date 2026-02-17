# 프로젝트 셋업 가이드

## 빠른 시작 (Quick Start)

### 1. MySQL 시작

```bash
docker-compose -f docker-compose.dev.yml up -d
```

### 2. 환경변수 설정

`backend/.env` 파일을 열어 Claude API 키 입력:

```bash
ANTHROPIC_API_KEY=sk-ant-xxxxx  # 실제 키로 변경
```

### 3. 백엔드 실행

```bash
# IntelliJ에서 backend 폴더를 열고
# NobleDailyApplication 실행

# 또는 터미널에서
cd backend
./gradlew bootRun
```

### 4. API 테스트

```bash
# 일기 작성 테스트
curl -X POST http://localhost:8080/api/diaries \
  -H "Content-Type: application/json" \
  -d '{
    "originalText": "오늘 너무 힘들었다. 야근 11시까지 함.",
    "persona": "JOSEON"
  }'
```

## 프로젝트 구조

```
noble-daily/
├── docker-compose.dev.yml     # MySQL 개발 환경
├── backend/
│   ├── build.gradle           # Gradle 설정
│   ├── .env                   # 환경변수 (gitignore)
│   ├── .env.example           # 환경변수 템플릿
│   ├── init.sql               # DB 스키마
│   └── src/main/java/com/noble/daily/
│       ├── NobleDailyApplication.java
│       ├── domain/            # 엔티티, Repository
│       │   ├── user/
│       │   └── diary/
│       ├── api/               # REST Controller
│       │   └── diary/
│       ├── external/          # 외부 API (Claude)
│       │   └── claude/
│       └── config/            # 설정
└── frontend/                  # (향후 추가)
```

## 주요 기능

### ✅ 완료된 기능

1. **데이터베이스 스키마**
   - DATETIME(6) 사용 (2038년 문제 해결)
   - emotions 테이블 통합 (1:1 관계 최적화)
   - N+1 문제 방지를 위한 JOIN FETCH

2. **JPA 엔티티**
   - User: OAuth 사용자
   - Diary: 일기 + 감정 분석
   - Persona: 4가지 페르소나 (enum)
   - EmotionAnalysis: 감정 분석 (@Embeddable)

3. **Claude API 통합**
   - 일기 변환 + 감정 분석 1번 호출
   - JSON structured output
   - 재시도 로직 (3회, exponential backoff)

4. **REST API**
   - POST /api/diaries (일기 작성)
   - GET /api/diaries (목록)
   - GET /api/diaries/{id} (상세)
   - PUT /api/diaries/{id} (수정)
   - DELETE /api/diaries/{id} (삭제)

### 🚧 예정된 기능

- OAuth2 인증 (Google, Kakao)
- 통계 API (월별, 감정 추이)
- SNS 공유 기능
- 프론트엔드 (React + TypeScript)

## Claude API 키 발급

1. https://console.anthropic.com/ 접속
2. 회원가입 및 로그인
3. API Keys 메뉴에서 새 키 생성
4. `backend/.env` 파일에 복사

## Git 커밋 로그

```bash
git log --oneline

# 결과:
# b495d5a feat: 일기 API 및 Service 구현
# b88c955 feat: Claude API 클라이언트 구현
# 0c19858 feat: JPA 엔티티 및 Repository 구현
# b775982 chore: Spring Boot 애플리케이션 설정 및 메인 클래스
# 87a44e1 chore: Spring Boot Gradle 프로젝트 설정
# 857bcad chore: 프로젝트 기본 설정 및 Docker 환경 구성
```

## 문의

문제가 발생하면 `backend/README.md`의 트러블슈팅 섹션을 참고하세요.
