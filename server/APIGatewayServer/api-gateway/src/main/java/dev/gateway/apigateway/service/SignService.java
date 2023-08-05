package dev.gateway.apigateway.service;


import dev.gateway.apigateway.dto.SignInResultDto;
import dev.gateway.apigateway.dto.SignUpResultDto;

public interface SignService {

    SignUpResultDto signUp(String email, String password, String name, String role);
    SignInResultDto signIn(String email, String password) throws RuntimeException;
}
