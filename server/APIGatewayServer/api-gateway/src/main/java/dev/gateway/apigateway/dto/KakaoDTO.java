package dev.gateway.apigateway.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoDTO {
    String token_type;
    String access_token;
    Integer expires_in;
    String refresh_token;
    String scope;
    Integer refresh_token_expires_in;

}
