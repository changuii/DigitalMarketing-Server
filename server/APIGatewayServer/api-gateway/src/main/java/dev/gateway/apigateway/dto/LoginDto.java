package dev.gateway.apigateway.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDto {

    private String email;
    private String password;
    private String name;
    private String gender;
    private String age;
    private String birthday;
    private String address;
}
