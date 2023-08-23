package dev.gateway.apigateway.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    public ResponseEntity<JSONObject> createReview(JSONObject json);
    public ResponseEntity<JSONObject> readAllByWriterReview(JSONObject json);
    public ResponseEntity<JSONObject> readAllReview();
    public ResponseEntity<JSONObject> updateReview(JSONObject json);
    public ResponseEntity<JSONObject> deleteReview(JSONObject json);


}
