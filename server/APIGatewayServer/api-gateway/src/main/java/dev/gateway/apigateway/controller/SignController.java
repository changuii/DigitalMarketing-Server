package dev.gateway.apigateway.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gateway.apigateway.dto.KakaoDTO;
import dev.gateway.apigateway.dto.LoginDto;
import dev.gateway.apigateway.service.SignService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

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


    // 테스트용 코드들
    @GetMapping
    public JSONObject kakaocodeTest(JSONObject jsonObject){
        return jsonObject;
    }

    @PostMapping("/test")
    public ResponseEntity<String> kakaoSign(){
        logger.info("인증, 인가 확인용");
        return ResponseEntity.status(201).body("성공");
    }

    @GetMapping("redir")
    public ResponseEntity<String> redir(){
        return ResponseEntity.status(401).body("redirect 되었습니다.");
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
        responseJSON.put("access_token", dto.getAccess_token());
        // 카카오에서 받은 토큰 반환
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
    // ==================================================================


    @PostMapping("/signin")
    public ResponseEntity<JSONObject> signIn(
            @RequestBody LoginDto loginDto
    ) throws RuntimeException{
        logger.info("[signIn] 로그인을 시도하고 있습니다. email : {} password : {}", loginDto.getEmail(), loginDto.getPassword());
        ResponseEntity<JSONObject> response = signService.signIn(loginDto.getEmail(), loginDto.getPassword());

        if(response.getStatusCode().value() == 200){
            logger.info("[signIn] 정상적으로 로그인되었습니다. email:{} token : {}", loginDto.getEmail(), response.getBody().get("access_token"));
        }

        return response;
    }


    @PostMapping("/signup")
    public ResponseEntity<JSONObject> signUp(
            @RequestBody LoginDto loginDto
    ){
        logger.info("[signUp] 회원가입을 수행합니다. email : {} password : {}", loginDto.getEmail(), loginDto.getPassword());
        // 이메일 비밀번호, 이름, 역할(어드민, 유저, 셀러), 성별, 연령대, 생일, 주소
        logger.info(loginDto.toString());
        if(signService.emailDuplicateCheck(loginDto.getEmail())){
            JSONObject json = new JSONObject();
            json.put("result", "Email Duplicate");
            return ResponseEntity.badRequest().body(json);
        }


        ResponseEntity response = signService.signUp(loginDto.getEmail(), loginDto.getPassword(), loginDto.getName(), "USER",
                loginDto.getGender(), loginDto.getAge(), loginDto.getBirthday(), loginDto.getAddress());

        if(response.getStatusCodeValue() == 201){
            logger.info("[signUp] 회원가입을 완료했습니다. email : {} password : {}", loginDto.getPassword(), loginDto.getPassword());
        }

        return response;
    }

    @PostMapping("/kakao/signup")
    public ResponseEntity<JSONObject> kakaoSignup(
            @RequestBody JSONObject jsonObject
    ) throws JsonProcessingException {
        String authCode = jsonObject.get("authCode").toString();

        String token = signService.kakaoCodeValidation(jsonObject);
        if(token.equals("fail")){
            logger.info("code검증 및 토큰 발급실패 : 회원가입 실패");
            return ResponseEntity.badRequest().body(new JSONObject());
        }
        JSONObject userData = signService.getKakoUserData(token);
        if(userData.get("result").equals("fail")){
            logger.info("kakao로부터 유저 데이터 요청 실패 : 회원가입 실패");
            return ResponseEntity.badRequest().body(new JSONObject());
        }
        LinkedHashMap kakaoAccount = (LinkedHashMap) userData.get("kakao_account");
        LinkedHashMap profile = (LinkedHashMap) kakaoAccount.get("profile");

        if(signService.emailDuplicateCheck(kakaoAccount.get("email").toString())){
            JSONObject json = new JSONObject();
            json.put("result", "Email Duplicate");
            return ResponseEntity.badRequest().body(json);
        }

        try{
            logger.info(kakaoAccount.get("email").toString());
            logger.info(profile.get("nickname").toString());
            logger.info(kakaoAccount.get("gender").toString());
            logger.info(kakaoAccount.get("age_range").toString());
            logger.info(kakaoAccount.get("birthday").toString());
        }catch(NullPointerException e){
            logger.info("약관 미동의 에러");
            return ResponseEntity.badRequest().body(new JSONObject());
        }
        signService.signUp(kakaoAccount.get("email").toString(),"KAKAO_USER" ,profile.get("nickname").toString(),
                "USER", kakaoAccount.get("gender").toString(), kakaoAccount.get("age_range").toString(),
                kakaoAccount.get("birthday").toString(), jsonObject.get("address").toString());


        return ResponseEntity.ok().body(jsonObject);
    }

    @PostMapping("/kakao/signin")
    public ResponseEntity<JSONObject> kakaoSignin(
            @RequestBody JSONObject jsonObject
    ) throws JsonProcessingException {
        String authCode = jsonObject.get("authCode").toString();

        String token = signService.kakaoCodeValidation(jsonObject);
        if(token.equals("fail")){
            logger.info("code검증 및 토큰 발급실패 : 로그인 실패");
            return ResponseEntity.badRequest().body(new JSONObject());
        }
        JSONObject userData = signService.getKakoUserData(token);
        if(userData.get("result").equals("fail")){
            logger.info("kakao로부터 유저 데이터 요청 실패 : 로그인 실패");
            return ResponseEntity.badRequest().body(new JSONObject());
        }

        LinkedHashMap kakaoAccount = (LinkedHashMap) userData.get("kakao_account");

        ResponseEntity response = signService.signIn(kakaoAccount.get("email").toString(), "KAKAO_USER");
        return response;
    }

    @PostMapping("/seller")
    public ResponseEntity<JSONObject> registrationSeller(
            @RequestParam String sellerNumber,
            @RequestParam String email
    )
    {
        signService.registrationSeller(email);
        JSONObject json = new JSONObject();
        json.put("result", "success");

        return ResponseEntity.ok().body(json);
    }
}
