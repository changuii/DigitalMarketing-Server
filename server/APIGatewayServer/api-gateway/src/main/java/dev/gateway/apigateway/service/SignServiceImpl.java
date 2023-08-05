package dev.gateway.apigateway.service;

import dev.gateway.apigateway.Entity.UserEntity;
import dev.gateway.apigateway.common.CommonResponse;
import dev.gateway.apigateway.config.JwtTokenProvider;
import dev.gateway.apigateway.dto.SignInResultDto;
import dev.gateway.apigateway.dto.SignUpResultDto;
import dev.gateway.apigateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;



@Service
public class SignServiceImpl implements SignService{

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


    @Override
    public SignUpResultDto signUp(String email, String password, String name, String role) {
        logger.info("[getSignUpResult] 회원가입 정보 전달");
        UserEntity userEntity;
        if(role.equalsIgnoreCase("admin")){
            userEntity = UserEntity.builder()
                    .uid(email)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else{
            userEntity = UserEntity.builder()
                    .uid(email)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();

        }

        UserEntity savedUser = userRepository.save(userEntity);
        SignUpResultDto signUpResultDto = new SignInResultDto();

        logger.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 값 주입");
        if(!savedUser.getUid().isEmpty()){
            logger.info("[getSignUpResult] 정상 처리 완료");
            setSuccessReslut(signUpResultDto);
        } else{
            logger.info("[getSignUpResult]  실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String email, String password) throws RuntimeException {
        logger.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        UserEntity userEntity = userRepository.getByUid(email);
        logger.info("[getSignInResult] e-mail : {}", email);

        logger.info("[getSignInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new RuntimeException();
        }

        logger.info("[getSignInResult] 패스워드 일치");

        logger.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(userEntity.getUid()), userEntity.getRoles()))
                .build();

        logger.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessReslut(signInResultDto);

        return signInResultDto;

    }


    private void setSuccessReslut(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}
