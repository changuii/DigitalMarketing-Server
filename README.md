# 소상공인을 위한 마케팅 플랫폼

| 이름   | 역할   | 기타   |
| ------ | ------ | ------ |
| 오민규 | 백엔드 | Spring, SalesPostServer|
| 이민재 | 백엔드 | Django, Review-PromotionalPostServer |
| 이종현 | 백엔드 | Spring, SalesPostServer |
| 이창의 | 백엔드 | Spring, ApiGatewayServer, Redis, Kafka |
| 한동근 | 백엔드 | Spring, ImageServer, SalesPostServer |
| 김수환 | 프론트 | NextJS |

아이디어 설명: https://www.notion.so/40183cd70217443286e4be23a6c46f70?pvs=4

API 링크: https://www.notion.so/API-0d6d6a3d1366441684bb60328ebf7c64?pvs=4



`front-end Repository`
- [링크](https://github.com/Suhwan-Front/Digital-marketing)

`Spring server`
- version : 2.7.14
- java : 11
- Gradle - Groovy 
- Java

`Django server`
| Package   | Version |
| --------- | ------- |
| Python    | 3.11.4  |
| Django    | 4.2.3   |
| pip       | 23.1.2  |

`Kafka cluster`
- zookeeper 1
- kafka 1
- wurstmeister/zookeeper:latest
- wurstmeister/kafka:latest


`redis`
- redis server 1
- redis 공식 이미지를 사용하여 배포


`배포`
- 네이버 클라우드 서버(우분투)를 통하여 컨테이너로 배포
- kafka, zookeeper : docker-compose 사용
- redis, MySQL : 공식 이미지 사용 

`구조`  
<img width="1454" alt="image" src="https://github.com/changuii/DigitalMarketing-Server/assets/122252160/c829afd3-72d2-43d6-b2e6-5d6964ecb4ca">



