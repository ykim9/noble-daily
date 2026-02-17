# 고귀한 하루 (Noble Daily)

> 평범한 일상을 고귀하게 기록하는 AI 일기 서비스

<br>

## ✨ 소개

오늘 하루도 고달팠나요?

별거 아닌 일상을 일기로 적으면, AI가 조선 영애나 브리저튼 귀족처럼 **극적으로** 바꿔드립니다.
슬픈 건 처연하게, 억울한 건 분노하게, 재밌는 건 더 웃기게! 🎭

**원본 일기**
```
두쫀쿠를 사기 위해 30분 줄서서 기다렸지만 내 앞에서 매진 되었다.
세상이 잘못된게 분명하다.
```

**🏯 조선 영애 변환**
```
소문으로 자자하던 그 귀한 과자를 손에 넣고자
이 몸이 반 시진을 줄을 서는 굴욕을 감내하였거늘,
하필 바로 내 앞에서 동이 나다니.
이는 하늘이 이 몸을 버린 것이 분명하도다.
오늘부로 세상을 믿지 않겠노라.
```

**🎩 브리저튼 귀족 변환**
```
소문이 자자한 그 디저트를 얻고자 30분을 기꺼이 기다렸건만,
하필 나의 차례 바로 앞에서 품절이 되다니.
이것은 단순한 불운이 아니라 이 세계의 질서가
근본적으로 잘못되었다는 명백한 증거임이 틀림없다.
나는 오늘 문명을 믿지 않기로 하였다.
```

<br>

## 🎭 페르소나

| 페르소나 | 스타일 |
|---------|--------|
| 🏯 조선 영애 | 사극체 (-도다, -로다, -옵니다) |
| 🎩 브리저튼 귀족 | 영국 리젠시 시대 귀족 말투 |
| 🐉 중국 황제/황후 | 중국 궁중 드라마 스타일 |
| ⚔️ 판타지 귀족 | 마법과 작위가 있는 판타지 세계관 |

<br>

## 📊 감정 분석

일기를 쓰면 AI가 감정을 분석하고 페르소나 스타일로 코멘트를 남겨드립니다.

```
억울함 ██████████ 90%
분노   ██████░░░░ 60%
절망   ████░░░░░░ 40%
기쁨   ░░░░░░░░░░  0%

💬 "전하, 이 얼마나 통탄할 일이옵니까.
    반 시진을 기다리셨거늘 바로 앞에서 동이 나다니,
    하늘도 무심하시지 않사옵니까."
```

<br>

## 🛠 기술 스택

### Frontend
![React](https://img.shields.io/badge/React-18-61DAFB?logo=react)
![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)

### Backend
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot)
![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)

### AI
![Claude](https://img.shields.io/badge/Claude_API-Anthropic-D97706)

### Infrastructure
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker)
![AWS](https://img.shields.io/badge/AWS_ECS_Fargate-232F3E?logo=amazonaws)

<br>

## 아키텍처

```
[React + TypeScript]  →  Vercel
         │
         ▼ REST API
[Spring Boot 3.x]     →  AWS ECS + Fargate
         │        │
         │        ▼
         │   [Claude API]
         │
         ▼
    [MySQL 8.0]       →  AWS RDS
```

<br>

## 로컬 실행 방법

### 사전 준비
- Java 17+
- Node.js 18+
- Docker Desktop

### 1. 저장소 클론
```bash
git clone https://github.com/{your-username}/noble-daily.git
cd noble-daily
```

### 2. 환경변수 설정
```bash
# backend/.env
cp backend/.env.example backend/.env
# ANTHROPIC_API_KEY, GOOGLE_CLIENT_ID 등 입력
```

### 3. MySQL 실행 (Docker)
```bash
docker-compose -f docker-compose.dev.yml up -d
```

### 4. 백엔드 실행
```bash
cd backend
./gradlew bootRun
# http://localhost:8080
```

### 5. 프론트엔드 실행
```bash
cd frontend
npm install
npm run dev
# http://localhost:3000
```

<br>

## 📁 프로젝트 구조

```
noble-daily/
├── README.md
├── REQUIREMENTS.md
├── docker-compose.dev.yml      # 로컬 개발용 (MySQL만)
├── docker-compose.yml          # 전체 Docker 실행용
├── backend/                    # Spring Boot
│   ├── Dockerfile
│   ├── .env.example
│   └── src/
└── frontend/                   # React + TypeScript
    ├── Dockerfile
    └── src/
```

<br>

## 상세 요건

[REQUIREMENTS.md](./REQUIREMENTS.md) 참고

<br>

## 라이센스

MIT License

<br>

---

> **"평범한 하루를 고귀하게"**
>
> Made with ☕ by Yeonsik
