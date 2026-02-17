# Noble Daily Backend

Spring Boot 3.x + JPA + Claude API 기반 백엔드

## 사전 준비

- Java 17 이상
- Docker Desktop (MySQL 실행용)
- IntelliJ IDEA (권장)

## 환경 설정

### 1. 환경변수 설정

`backend/.env` 파일을 열어 실제 API 키로 수정:

```bash
# Claude API 키 입력 (https://console.anthropic.com/)
ANTHROPIC_API_KEY=sk-ant-xxxxx

# Google OAuth 설정 (현재는 선택사항)
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
```

### 2. MySQL 시작

```bash
# 프로젝트 루트에서
docker-compose -f docker-compose.dev.yml up -d

# MySQL 로그 확인
docker-compose -f docker-compose.dev.yml logs -f mysql
```

### 3. MySQL 접속 확인

```bash
# 컨테이너 접속
docker exec -it noble-daily-db mysql -unoble -pnoble1234 noble_diary

# 테이블 확인
SHOW TABLES;
```

## 실행 방법

### IntelliJ에서 실행 (권장)

1. IntelliJ로 `backend` 폴더 열기
2. Gradle 자동 import 완료 대기
3. `NobleDailyApplication` 우클릭 → Run
4. http://localhost:8080 접속

### Gradle 명령어로 실행

```bash
cd backend

# Mac/Linux
./gradlew bootRun

# Windows
gradlew.bat bootRun
```

## API 테스트

### Health Check

```bash
curl http://localhost:8080/actuator/health
```

### 일기 작성

```bash
curl -X POST http://localhost:8080/api/diaries \
  -H "Content-Type: application/json" \
  -d '{
    "originalText": "오늘 회사에서 계약직이 또 이상한 코드 짜놨음. 리뷰하다가 화남. 11시까지 야근함.",
    "persona": "JOSEON"
  }'
```

### 일기 목록 조회

```bash
curl http://localhost:8080/api/diaries
```

### 일기 상세 조회

```bash
curl http://localhost:8080/api/diaries/1
```

## 페르소나 타입

- `JOSEON`: 조선 영애
- `BRIDGERTON`: 브리저튼 귀족
- `CHINESE`: 중국 황제
- `FANTASY`: 판타지 귀족

## 트러블슈팅

### MySQL 연결 실패

```bash
# MySQL 컨테이너 재시작
docker-compose -f docker-compose.dev.yml restart mysql

# 포트 충돌 확인
lsof -i :3306
```

### Gradle 빌드 실패

```bash
# Gradle 캐시 삭제
./gradlew clean

# 다시 빌드
./gradlew build
```

### Claude API 호출 실패

- `.env` 파일의 `ANTHROPIC_API_KEY` 확인
- API 키 유효성 확인
- 네트워크 연결 확인

## 다음 단계

- [ ] OAuth2 인증 구현
- [ ] 일기 통계 API
- [ ] SNS 공유 기능
- [ ] 프론트엔드 연동
