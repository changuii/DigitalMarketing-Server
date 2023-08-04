# API Gateway


| Dependency       | Maven |
| ---------------- | ----- |
| Spring Security  |       |
| Spring Web       |       |
| Spring for kafka |       |
| lombok           |       |
| simple-json |     [link](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple/1.1.1)  |


## Simple JSON

보다 쉽게 JSON을 구성하기 위한 의존성  

예제코드
```java
    JSONObject json = new JSONObject();
    json.put("title", message.getTitle());
    json.put("text", message.getText());
```

해당 JSON 파일을 Kafka에서 믿을 수 있도록 application.yml을 수정해주어야 한다.  
또한 value값에 대한 직렬화와 역직렬화를 해주어야 한다.   
```yml
spring:
  kafka:
    consumer:
    value-serializer: org.springframework.kafka.support.serializer.JsonDeserializer
     properties:
        spring:
          json:
            trusted:
              packages: org.json.simple
    producer:
        value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

