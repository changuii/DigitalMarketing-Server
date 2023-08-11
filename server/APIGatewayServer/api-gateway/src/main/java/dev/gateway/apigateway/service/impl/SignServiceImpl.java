package dev.gateway.apigateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gateway.apigateway.Entity.UserEntity;
import dev.gateway.apigateway.config.security.JwtTokenProvider;
import dev.gateway.apigateway.dto.KakaoDTO;
import dev.gateway.apigateway.repository.UserRepository;
import dev.gateway.apigateway.service.SignService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;



@Service
public class SignServiceImpl implements SignService {

    private static final Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailDuplicateCheck(String email){
        boolean check = userRepository.isEmailDuplicateCheck(email);
        if(check){
            return true;
        }
        return false;
    }

    public void registrationSeller(String uid){
        userRepository.updateRoleByUid(uid, "ROLE_SELLER");
    }



    @Override
    public ResponseEntity<JSONObject> signUp(String email, String password, String name, String role, String gender, String age, String birthday, String address) {
        logger.info("[getSignUpResult] 회원가입 정보 전달");
        UserEntity userEntity;
        // 규칙 설정
        if(role.equalsIgnoreCase("admin")){
            userEntity = UserEntity.builder()
                    .uid(email)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .gender(gender)
                    .age(age)
                    .birthday(birthday)
                    .address(address)
                    .build();
        } else if(role.equalsIgnoreCase("seller")){
            userEntity = UserEntity.builder()
                    .uid(email)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_SELLER"))
                    .gender(gender)
                    .age(age)
                    .birthday(birthday)
                    .address(address)
                    .build();

        } else {
            userEntity = UserEntity.builder()
                    .uid(email)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .gender(gender)
                    .age(age)
                    .birthday(birthday)
                    .address(address)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

        }

        // 생성한 유저 엔티티를 DB에 저장
        UserEntity savedUser = userRepository.save(userEntity);
        JSONObject json = new JSONObject();

//        SignUpResultDto signUpResultDto = new SignInResultDto();

        logger.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 값 주입");
        if(!savedUser.getUid().isEmpty()){
            logger.info("[getSignUpResult] 정상 처리 완료");
            json.put("result", "success");
            return ResponseEntity.status(201).body(json);
//            setSuccessReslut(signUpResultDto);
        } else{
            logger.info("[getSignUpResult]  실패 처리 완료");
            json.put("result", "email Null");
            return ResponseEntity.badRequest().body(json);
//            setFailResult(signUpResultDto);

        }

    }

    @Override
    public ResponseEntity<JSONObject> signIn(String email, String password) throws RuntimeException {
        logger.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        UserEntity userEntity = userRepository.getByUid(email);
        logger.info("[getSignInResult] e-mail : {}", email);

        logger.info("[getSignInResult] 패스워드 비교 수행");
        // 패스워드 불일치
        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            JSONObject json = new JSONObject();
            json.put("access_token", "fail");
            return ResponseEntity.badRequest().body(json);
        }

        logger.info("[getSignInResult] 패스워드 일치");
        JSONObject json = new JSONObject();
        json.put("access_token", jwtTokenProvider.createToken(String.valueOf(userEntity.getUid()), userEntity.getRoles()));


//        logger.info("[getSignInResult] SignInResultDto 객체 생성");
//        SignInResultDto signInResultDto = SignInResultDto.builder()
//                .token(jwtTokenProvider.createToken(String.valueOf(userEntity.getUid()), userEntity.getRoles()))
//                .build();
//
//        logger.info("[getSignInResult] SignInResultDto 객체에 값 주입");
//        setSuccessReslut(signInResultDto);

        return ResponseEntity.status(200).body(json);

    }


    // 발급된 카카오 코드가 유효한지를 검증
    public String kakaoCodeValidation(JSONObject jsonObject) throws JsonProcessingException {
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
        body.add("redirect_uri", "http://localhost:8080/auth/kakaotest");
        // 인가코드
        body.add("code", jsonObject.get("authCode").toString());
        // 설정
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // API 호출
        String url = "https://kauth.kakao.com/oauth/token";
        ResponseEntity<String> responseEntity;
        try{
            responseEntity = restTemplate.postForEntity(url, requestMessage, String.class);
        }catch (Exception e){
            logger.info("카카오 토큰 발급 실패");
            return "fail";
        }
        logger.info("카카오 토큰 발급 성공");

        HttpEntity<String> response = responseEntity;
        // String으로 들어온 response값을 parsing
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        KakaoDTO dto = objectMapper.readValue(response.getBody(), KakaoDTO.class);

        logger.info(dto.getAccess_token());



        return dto.getAccess_token();

    }

    public JSONObject getKakoUserData( String accessToken) throws JsonProcessingException {
        // REST API 호출을 위한 template
        RestTemplate restTemplate = new RestTemplate();
        // Header 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "bearer "+accessToken);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 설정
        HttpEntity<?> requestMessage = new HttpEntity<>(httpHeaders);

        // API 호출
        String url = "https://kapi.kakao.com/v2/user/me";
        ResponseEntity<String> responseEntity;

        try{
            responseEntity = restTemplate.postForEntity(url, requestMessage, String.class);
        } catch (Exception e){
            logger.info("카카오 유저 정보 발급 실패");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "fail");
            return jsonObject;
        }

        logger.info("카카오 유저 정보 발급 성공");

        logger.info(responseEntity.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        JSONObject json = objectMapper.readValue(responseEntity.getBody(), JSONObject.class);

        json.put("result", "success");
        logger.info(json.toJSONString());
        return json;
    }


//    private void setSuccessReslut(SignUpResultDto result){
//        result.setSuccess(true);
//        result.setCode(CommonResponse.SUCCESS.getCode());
//        result.setMsg(CommonResponse.SUCCESS.getMsg());
//    }
//
//    private void setFailResult(SignUpResultDto result){
//        result.setSuccess(false);
//        result.setCode(CommonResponse.FAIL.getCode());
//        result.setMsg(CommonResponse.FAIL.getMsg());
//    }
}
