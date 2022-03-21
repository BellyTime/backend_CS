# backend_CS
bellytime 고객용 백엔드 서버

### 프로젝트 설명

주기때마다 내가 먹고 싶은 음식을 알람받고, 설정하세요!


### 핵심기능

1. 로그인을 통한 개인 프로필을 제공합니다
2. 친구, 가게를 팔로우 할 수있고 포스팅을 구독할 수 있습니다.
3. 가까운 가게들을 조회할 수 있고, 필터링하여 정렬 할 수 있습니다.
4. 가게를 예약할 수 있습니다.
5. 친구와 가게 사장님간의 채팅을 할 수 있습니다.
6. 쿨타임을 설정하고 해당 쿨타임에 음식에 대한 알림을 받아 볼 수 있습니다.

### 현재 구성도
<img width="1241" alt="스크린샷 2022-03-21 오전 9 04 36" src="https://user-images.githubusercontent.com/54499829/159191784-bb6eb58b-b635-43a3-a132-79521c76b00d.png">



### 백엔드 기술 스택
1. springboot
2. junit5
3. redis
4. mysql
5. ELK
6. docker
7. spring security
8. spring batch
9. flyway
10. jpa
11. websocket
  
  
dir
```
src
    ├── main
    │   ├── generated
    │   ├── java
    │   │   └── malangcute
    │   │       └── bellytime
    │   │           └── bellytimeCustomer
    │   │               ├── alarm
    │   │               ├── chat
    │   │               │   ├── config
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── comment
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── cooltime
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── feed
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── follow
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── food
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   │   └── elastic
    │   │               │   └── service
    │   │               ├── global
    │   │               │   ├── aop
    │   │               │   ├── auth
    │   │               │   │   ├── controller
    │   │               │   │   ├── domain
    │   │               │   │   ├── dto
    │   │               │   │   ├── oauth
    │   │               │   │   ├── service
    │   │               │   │   └── util
    │   │               │   ├── aws
    │   │               │   ├── config
    │   │               │   ├── domain
    │   │               │   │   └── common
    │   │               │   ├── exception
    │   │               │   │   ├── dto
    │   │               │   │   └── exceptionDetail
    │   │               │   └── schedule
    │   │               │       ├── batch
    │   │               │       └── quartz
    │   │               ├── reservation
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── search
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   └── service
    │   │               ├── shop
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── repository
    │   │               │   │   └── elastic
    │   │               │   └── service
    │   │               └── user
    │   │                   ├── controller
    │   │                   ├── domain
    │   │                   ├── dto
    │   │                   ├── repository
    │   │                   └── service
    │   └── resources
    │       ├── META-INF
    │       ├── db
    │       │   └── migration
    │       ├── static
    │       └── templates
    └── test
        ├── generated_tests
        ├── java
        │   └── malangcute
        │       └── bellytime
        │           └── bellytimeCustomer
        │               ├── config
        │               ├── global
        │               │   └── auth
        │               │       ├── controller
        │               │       └── service
        │               └── user
        │                   ├── controller
        │                   └── service
        └── resources
            └── testdb
                └── migration
```
