# DigitalMarketing-Server




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


