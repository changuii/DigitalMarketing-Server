package dev.gateway.apigateway.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class KakaoAuthorization {
    Long id;
    Integer expires_in;
    Integer app_id;

}
