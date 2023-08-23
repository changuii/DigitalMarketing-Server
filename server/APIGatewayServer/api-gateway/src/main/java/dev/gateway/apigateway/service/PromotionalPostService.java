package dev.gateway.apigateway.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PromotionalPostService {

    public ResponseEntity<JSONObject> createPromotionalPost(JSONObject json, List<MultipartFile> img) throws IOException;
    public ResponseEntity<JSONObject> readPromotionalPost(JSONObject json);
    public ResponseEntity<JSONObject> readAllPromotionalPost();
    public ResponseEntity<JSONObject> updatePromotionalPost(JSONObject json);
    public ResponseEntity<JSONObject> deletePromotionalPost(JSONObject json);
    public ResponseEntity<JSONObject> categoryReadAllPromotionalPost(JSONObject json);
    public ResponseEntity<JSONObject> tagReadAllPromotionalPost(JSONObject json);
    public ResponseEntity<JSONObject> createComment(JSONObject json);
    public ResponseEntity<JSONObject> updateComment(JSONObject json);
    public ResponseEntity<JSONObject> deleteComment(JSONObject json);
    public ResponseEntity<JSONObject> postLike(JSONObject json);
    public ResponseEntity<JSONObject> commentLike(JSONObject json);
    public ResponseEntity<JSONObject> readBySalesPostNumber(JSONObject json);
}
