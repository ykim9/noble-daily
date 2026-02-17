# 🚀 빠른 시작 가이드

## 1단계: MySQL 시작 (30초)

```bash
# 프로젝트 루트에서
docker-compose -f docker-compose.dev.yml up -d

# 확인
docker ps | grep noble-daily-db
```

## 2단계: 환경변수 설정 (1분)

`backend/.env` 파일을 생성하고 Claude API 키를 입력하세요:

```bash
# backend/.env 파일 생성
cp backend/.env.example backend/.env

# 편집기로 열어서 ANTHROPIC_API_KEY 수정
# ANTHROPIC_API_KEY=sk-ant-xxxxx  (실제 키로 변경)
```

### Claude API 키 발급 방법
1. https://console.anthropic.com/ 접속
2. 회원가입 및 로그인
3. API Keys 메뉴 → Create Key
4. 생성된 키를 복사

## 3단계: IntelliJ에서 백엔드 실행 (2분)

### 방법 1: IntelliJ (권장) ⭐️

1. **IntelliJ IDEA** 실행
2. **Open** → `backend` 폴더 선택
3. Gradle 자동 import 대기 (우하단 진행률 확인)
4. `src/main/java/com/noble/daily/NobleDailyApplication.java` 우클릭
5. **Run 'NobleDailyApplication'**
6. 로그에서 `Started NobleDailyApplication` 확인

### 방법 2: 터미널

```bash
cd backend

# gradlew 실행 권한 부여 (최초 1회)
chmod +x gradlew

# 실행 (처음엔 의존성 다운로드로 시간 소요)
./gradlew bootRun
```

## 4단계: API 테스트 (1분)

새 터미널을 열고 테스트:

```bash
# Health Check
curl http://localhost:8080/actuator/health

# 일기 작성 테스트 (조선 영애)
curl -X POST http://localhost:8080/api/diaries \
  -H "Content-Type: application/json" \
  -d '{
    "originalText": "오늘 회사에서 계약직이 또 이상한 코드 짜놨음. 리뷰하다가 화남. 11시까지 야근함.",
    "persona": "JOSEON"
  }'

# 일기 목록 조회
curl http://localhost:8080/api/diaries
```

### 응답 예시

```json
{
  "id": 1,
  "originalText": "오늘 회사에서...",
  "persona": "JOSEON",
  "convertedText": "오늘도 궁중의 무능한 환관이 짐의 심기를 불편하게...",
  "emotions": {
    "anger": 70,
    "sadness": 20,
    "joy": 0,
    "anxiety": 30,
    "fatigue": 80
  },
  "aiComment": "전하, 오늘 용안이 심히 일그러지셨사옵니다...",
  "createdAt": "2026-02-17T17:30:00.123456",
  "updatedAt": "2026-02-17T17:30:00.123456"
}
```

## 다른 페르소나 테스트

### 브리저튼 귀족
```bash
curl -X POST http://localhost:8080/api/diaries \
  -H "Content-Type: application/json" \
  -d '{
    "originalText": "두쫀쿠를 사기 위해 30분 줄서서 기다렸지만 내 앞에서 매진 되었다.",
    "persona": "BRIDGERTON"
  }'
```

### 중국 황제
```bash
curl -X POST http://localhost:8080/api/diaries \
  -H "Content-Type: application/json" \
  -d '{
    "originalText": "점심에 돈까스 먹었는데 너무 맛있었다. 행복함.",
    "persona": "CHINESE"
  }'
```

### 판타지 귀족
```bash
curl -X POST http://localhost:8080/api/diaries \
  -H "Content-Type: application/json" \
  -d '{
    "originalText": "오늘 드디어 승진했다! 연봉도 올랐다!",
    "persona": "FANTASY"
  }'
```

## 트러블슈팅 🔧

### MySQL 연결 실패
```bash
# MySQL 재시작
docker-compose -f docker-compose.dev.yml restart mysql

# 로그 확인
docker-compose -f docker-compose.dev.yml logs mysql
```

### Port 8080 already in use
```bash
# 8080 포트 사용 프로세스 확인
lsof -i :8080

# 종료 후 다시 실행
```

### Claude API 호출 실패
- `backend/.env` 파일의 `ANTHROPIC_API_KEY` 확인
- API 키 앞뒤 공백 제거
- 키 형식: `sk-ant-api03-xxxxx`

### Gradle build 실패
```bash
cd backend

# 캐시 삭제
./gradlew clean

# 다시 빌드
./gradlew build
```

## 종료 방법

```bash
# MySQL 종료
docker-compose -f docker-compose.dev.yml down

# IntelliJ에서 Stop 버튼 클릭
# 또는 터미널에서 Ctrl+C
```

## 다음 단계

- [ ] OAuth 인증 추가 (Google, Kakao)
- [ ] 통계 API 구현
- [ ] 프론트엔드 개발 (React)
- [ ] Docker 전체 컨테이너화
- [ ] AWS 배포

---

**문제가 있나요?** `backend/README.md`의 트러블슈팅 섹션을 참고하세요.
