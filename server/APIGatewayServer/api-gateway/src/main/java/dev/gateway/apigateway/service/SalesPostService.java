package dev.gateway.apigateway.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface SalesPostService {
    public ResponseEntity<JSONObject> createSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> readRecentByWriterSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> readAllByWriterSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> readAllSalesPost();
    public ResponseEntity<JSONObject> updateSalesPost(JSONObject json);
    public ResponseEntity<JSONObject> deleteSalesPost(JSONObject json);


}
