//package dev.gateway.apigateway.config;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dev.gateway.apigateway.Entity.KakaoUserDetails;
//import dev.gateway.apigateway.dto.KakaoAuthorization;
//import org.springframework.http.*;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//
//// API 요청이 매핑되기전에 걸러주는(Jwt Token을 확인하고) 필터
//public class KakaoJwtTokenFilter extends GenericFilterBean {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String token = ((HttpServletRequest) request).getHeader("Authorization");
//
//        // 토큰 검증 절차
//        // REST API 호출을 위한 template
//        RestTemplate restTemplate = new RestTemplate();
//        // Header 설정
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer "+token);
//        // 설정
//        HttpEntity<?> requestMessage = new HttpEntity<>(httpHeaders);
//
//        // API 호출
//        String url = "https://kapi.kakao.com/v1/user/access_token_info";
//        ResponseEntity<String> apiResponse = restTemplate.exchange(url, HttpMethod.GET,requestMessage, String.class);
//        logger.info(apiResponse.getBody());
//        if(
//                !(apiResponse.getStatusCodeValue() == 400) &&
//        !(apiResponse.getStatusCodeValue() == 401))
//        {
//            // 검증완료
//            // String으로 들어온 response값을 parsing
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//            KakaoAuthorization dto = objectMapper.readValue(apiResponse.getBody(), KakaoAuthorization.class);
//            logger.info(dto.toString());
////            Authentication authentication;
////            UserDetails userDetails =
////            SecurityContextHolder.getContext().setAuthentication();
//            Authentication authentication;
//           // KakaoUserDetails kakaoUserDetails = new KakaoUserDetails(
//            //        dto.getId(),
//            //);
//            UserDetails userDetails =
//            authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//        }
//
//    }
//}
