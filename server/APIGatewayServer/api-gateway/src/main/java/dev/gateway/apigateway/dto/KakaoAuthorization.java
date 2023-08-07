package dev.gateway.apigateway.dto;


import lombok.*;


// test 용도 필요 x
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
