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

## 카카오 로그인 관련

로그인 페이지  
kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code

```
https://kauth.kakao.com/oauth/authorize?client_id=066776e452014ee0743de831d167b35a&redirect_uri=http://localhost:8080/auth/kakao&response_type=code
```

`액세스 토큰 요청으로 받을 수 있는 정보`  

```java
public class KakaoDTO {
    String token_type;
    String access_token;
    Integer expires_in;
    String refresh_token;
    String scope;
    Integer refresh_token_expires_in;

}
```

카카오 유저정보 json 형태
```json
{
  "id":2949133506,
  "connected_at":"2023-08-04T16:11:06Z",
  "properties":{
    "nickname":"창의",
    "profile_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
    "thumbnail_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"},
  "kakao_account":{
    "profile_nickname_needs_agreement":false,
    "profile_image_needs_agreement":false,
  "profile":{
    "nickname":"창의",
    "thumbnail_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg",
    "profile_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
    "is_default_image":true},
  "has_email":true,
  "email_needs_agreement":false,
  "is_email_valid":true,
  "is_email_verified":true,
  "email":"rhljh201@daum.net"}
}
```

필요한 데이터 : 닉네임, 이메일, 성별, 연령대, 생일


