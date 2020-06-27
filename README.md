# 카카오페이 사전과제 - 카카오페이 뿌리기 기능 구현하기
## 목차
- [개발 환경](#개발-환경)
- [API 명세](#API-명세)
---

## 개발 환경
- 기본 환경
    - OS: Mac OS X
- Server
    - Java8
    - Spring Boot 2.3.1
    - JPA
    - H2
    - Gradle
    - Junit4


## API-명세
### 1. 뿌리기 API
- Request

```
http://localhost:8080/v1/pay/distribute
```

```
POST /v1/pay/distribute HTTP/1.1
```

- Response

```json
{
  "code": "00",
  "message": null,
  "body": "TK%"
}
```
#### 핵심 문제해결 전략
- 토큰 생성
  - distribute table 에 id 값을 unicode 로 변경 65535 * 65535 표현 가능
  - TokenSecurityConverter 가 unicode 를 encrypt / decrypt 함
- 분배 전략
  - 분배 전략을 전략 ( randomDistributeStrategy ) 을 service 에 주입하게 구현


### 2. 받기 API
- Request

```
http://localhost:8080/v1/pay/distribute/TK%
```

```
PUT /v1/pay/distribute/TK% HTTP/1.1
```

- Response

```json
{
  "code": "00",
  "message": null,
  "body": 1067
}
```
#### 핵심 문제해결 전략
- 다수의 서버에 다수의 인스턴스로 동작하더라도 기능에
  문제가 없도록 설계
  - repository 에 custom 비관적락 으로 update 제어
  ```
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
  @Query("SELECT distribute FROM Distribute distribute join fetch distribute.recipients WHERE distribute.id = ?1")
  Distribute findByIdWithRocking(int distributeId);
  ```
  Distribute OneToMany FetchType.EAGER 로 설정했기 때문에 JPQL 사용시 N+1 문제가 생기므로 join fetch 사용
- checked Exception 으로 httpStatus OK 이지만 내부 규정 에러 처리
- invalid 한 토큰 httpStatus UNAUTHORIZED 리턴

### 3. 조회 API
- Request

```
http://localhost:8080/v1/pay/distribute/TK%
```

```
GET /v1/pay/distribute/TK% HTTP/1.1
```

- Response

```json
{
  "code": "00",
  "message": null,
  "body": {
    "amount": 12000,
    "userCount": 2,
    "regDate": "2020-06-27T13:00:06.864234",
    "amountReceive": 10321,
    "recipients": [
      {
        "userId": 3,
        "amount": 1686
      },
      {
        "userId": 2,
        "amount": 8635
      }
    ]
  }
}
```
#### 핵심 문제해결 전략
- 컨버터로 요구사항 데이터 컨버팅
  - distributeConverter
- checked Exception 으로 httpStatus OK 이지만 내부 규정 에러 처리
- invalid 한 토큰 httpStatus UNAUTHORIZED 리턴
