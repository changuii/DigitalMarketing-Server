package dev.gateway.apigateway.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SalesPostService {

    public ResponseEntity<JSONObject> createSalesPost(JSONObject json, List<MultipartFile> image) throws IOException;
    public ResponseEntity<JSONObject> readBySalesPostNumber(long id);
    public ResponseEntity<JSONObject> readRecentByWriterSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> readAllByWriterSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> readAllSalesPost();
    public ResponseEntity<JSONObject> updateSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> deleteSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> postLikeSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> hitCountSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> readAllByCategorySalesPost(String category);
    public ResponseEntity<JSONObject> createComment(JSONObject json);
    public ResponseEntity<JSONObject> deleteComment(JSONObject json);

}
