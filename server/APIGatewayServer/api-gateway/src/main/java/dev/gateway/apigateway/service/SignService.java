package dev.gateway.apigateway.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface SignService {

    ResponseEntity<JSONObject>  signUp(String email, String password, String name, String role
    ,String gender, String age, String birthday, String address );
    ResponseEntity<JSONObject> signIn(String email, String password) throws RuntimeException;
    public JSONObject getKakoUserData( String accessToken)throws JsonProcessingException;
    public String kakaoCodeValidation(JSONObject jsonObject) throws JsonProcessingException;
    public boolean emailDuplicateCheck(String email);
    public void registrationSeller(String uid);
}
