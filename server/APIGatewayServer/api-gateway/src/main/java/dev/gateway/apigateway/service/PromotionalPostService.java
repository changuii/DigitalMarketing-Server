package dev.gateway.apigateway.service;


import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface PromotionalPostService {

    public ResponseEntity<JSONObject> createPromotionalPost(JSONObject json);
    public ResponseEntity<JSONObject> readAllPromotionalPost();
    public ResponseEntity<JSONObject> updatePromotionalPost(JSONObject json);
    public ResponseEntity<JSONObject> deletePromotionalPost(JSONObject json);

}
