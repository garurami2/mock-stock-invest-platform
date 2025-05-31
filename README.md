Python 학습용 프로젝트


# 🤖 AI 기반 비트코인 자동매매 시스템

Python 기반의 인공지능(AI) 비트코인 자동 매매 봇입니다.  
기술적 지표, 공포탐욕지수, 뉴스, 유튜브 콘텐츠 분석, 과거 거래 반성 기록 등을 종합해 **AI가 매수/매도/보유 판단을 내리고 실행**합니다.

버전 : Python 3.12
사용 lib : OpenAI, pyupbit, selenium, pandas, youtube_transcript_api
사용 DB : SQLite

---

## 📌 주요 기능

### 1. AI 거래 판단 및 실행
- OpenAI API를 통해 시장 데이터 종합 분석 후 `매수(buy)`, `매도(sell)`, `보유(hold)` 중 하나를 선택
- 판단 근거, 신뢰도, 리스크 요인, 개선 포인트 포함한 상세 JSON 응답 기반 거래

### 2. 기술적 분석 (TA)
- `ta` 라이브러리를 활용한 RSI, MACD, Bollinger Band, 이동평균선(SMA) 계산
- 정배열/역배열 판단, 골든크로스/데드크로스 감지
- 최근 가격의 지지선/저항선 위치 파악

### 3. 뉴스 및 유튜브 분석
- Google 뉴스 검색을 통한 실시간 암호화폐 뉴스 요약
- 지정 유튜브 채널의 자막 내용을 AI로 분석하여 시장 심리 및 주요 이슈 추출

### 4. 거래 반성 기록 시스템
- 과거 거래 데이터를 분석해 AI가 성공률, 결정 분석, 개선점 등을 반성 일기로 저장
- 데이터는 SQLite (`trading.sqlite`) 기반으로 관리

### 5. 자동 차트 캡처 및 분석
- Selenium을 통해 업비트 차트를 자동 캡처
- 볼린저 밴드 등 지표 추가 후 이미지 분석 기반 AI 판단에 반영

### 6. 모니터링 대시보드
- `streamlit` 기반 실시간 시각화 웹 대시보드
- 잔고, 수익률, 거래 기록, AI 반성 일기, 손익 분석 등 제공

---

## 🛠️ 사용 기술 스택

| 파일명               | 설명 |
|--------------------|------|
| `autotrade.py`      | 자동매매의 메인 로직. AI 분석, 거래 실행, 지표 수집, 반성기록까지 포함 |
| `charts.py`         | 업비트 차트 캡처 자동화 (Selenium 활용) |
| `moving_aver.py`    | 이동평균선 정렬, 골든/데드크로스, 지지/저항선 분석 |
| `youtube_api.py`    | 유튜브 자막 분석 및 요약 (GPT API 기반) |
| `DatabaseManager.py`| 거래 기록 및 반성일기를 SQLite에 저장/조회 |
| `streamlit-app.py`  | 시각화 대시보드 구성 (`streamlit` + `plotly`) |
| `.env`              | OpenAI, Upbit, SerpAPI 키 저장용 환경 설정 파일 (보안 필요) |

---

## 🗂️ 주요 파일 설명

| 파일명              | 설명 |
|-------------------|------|
| `autotrade.py`     | 전체 자동매매 로직. AI 분석, 거래 실행, 지표 계산 포함 |
| `charts.py`        | 업비트 차트 자동 캡처 및 지표 추가 (볼린저 밴드 등) |
| `moving_aver.py`   | 이동평균선 분석 및 크로스, 배열, 지지/저항선 감지 |
| `youtube_api.py`   | 유튜브 자막 수집 및 OpenAI 기반 분석 |
| `DatabaseManager.py` | SQLite 기반 거래 및 반성 일기 저장/조회 |
| `streamlit-app.py` | 시각화 대시보드 (거래 요약, 손익 분석, 반성 보기 등) |

---

## ✅ 실행 방법

1. **환경 변수 설정 (.env)**
```env
UPBIT_ACCESS_KEY=your_upbit_access_key
UPBIT_SECRET_KEY=your_upbit_secret_key
SERPAPI_KEY=your_serpapi_key
OPENAI_API_KEY=your_openai_api_key
