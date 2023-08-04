package dev.gateway.apigateway.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gateway.apigateway.dto.KakaoDTO;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/outh")
public class SignController {

    private final static Logger logger = LoggerFactory.getLogger(SignController.class);



    @PostMapping
    public void kakaoSign(){

    }

    @GetMapping("/kakao")
    public JSONObject kakaoCode(
            @RequestParam String code
    ) throws JsonProcessingException {
        JSONObject json = new JSONObject();
        json.put("result", "성공적으로 카카오 API를 불러왔습니다.");
        json.put("message", "SUCCESS");
        json.put("code", code);
        logger.info("KAKAO API CODE : ", code);

        // REST API 호출을 위한 template
        RestTemplate restTemplate = new RestTemplate();
        // Header 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Body 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // 고정 값
        body.add("grant_type", "authorization_code");
        // REST API KEY
        body.add("client_id", "066776e452014ee0743de831d167b35a");
        // 리다이렉트 uri
        body.add("redirect_uri", "http://localhost:8080/outh/kakao");
        // 인가코드
        body.add("code", code);
        // 설정
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // API 호출
        String url = "https://kauth.kakao.com/oauth/token";
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);
        logger.info(response.getBody());


        // String으로 들어온 response값을 parsing
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        KakaoDTO dto = objectMapper.readValue(response.getBody(), KakaoDTO.class);

        logger.info(dto.getAccess_token());

        return json;
    }




}
