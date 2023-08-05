package dev.gateway.apigateway.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gateway.apigateway.dto.KakaoDTO;
import dev.gateway.apigateway.dto.LoginDto;
import dev.gateway.apigateway.dto.SignInResultDto;
import dev.gateway.apigateway.dto.SignUpResultDto;
import dev.gateway.apigateway.service.SignService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class SignController {

    private final static Logger logger = LoggerFactory.getLogger(SignController.class);

    private final SignService signService;

    public SignController(
            @Autowired SignService signService
    ){
        this.signService = signService;
    }

    @PostMapping
    public void kakaoSign(){

    }

    @GetMapping("/token")
    public ResponseEntity<JSONObject> createToken(
            @RequestHeader String code
    ) throws JsonProcessingException {

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
        body.add("redirect_uri", "http://localhost:3000/");
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
        logger.info("token 생성 완료");
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("token", dto.getAccess_token());
        return ResponseEntity.status(201).body(responseJSON);
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

;

    @PostMapping("signin")
    public ResponseEntity<JSONObject> signIn(
            @RequestBody LoginDto loginDto
    ) throws RuntimeException{
        logger.info("[signIn] 로그인을 시도하고 있습니다. email : {} password : {}", loginDto.getEmail(), loginDto.getPassword());
        SignInResultDto signInResultDto = signService.signIn(loginDto.getEmail(), loginDto.getPassword());

        if(signInResultDto.getCode() == 0){
            logger.info("[signIn] 정상적으로 로그인되었습니다. email:{} token : {}", loginDto.getEmail(), signInResultDto.getToken());
        }


        JSONObject json = new JSONObject();
        json.put("access_token", signInResultDto.getToken());
        return ResponseEntity.status(200).body(json);
    }


    @PostMapping("/signup")
    public ResponseEntity<JSONObject> signUp(
            @RequestBody LoginDto loginDto
    ){
        logger.info("[signUp] 회원가입을 수행합니다. email : {} password : {}", loginDto.getEmail(), loginDto.getPassword());
        SignUpResultDto signUpResultDto = signService.signUp(loginDto.getEmail(), loginDto.getPassword(), "test", "USER");
        logger.info("[signUp] 회원가입을 완료했습니다. email : {} password : {}", loginDto.getPassword(), loginDto.getPassword() );

        JSONObject json = new JSONObject();
        json.put("email", loginDto.getEmail());
        json.put("password", loginDto.getPassword());

        return ResponseEntity.status(201).body(json);
    }



}
