package dev.gateway.apigateway.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface InquiryService {


    public ResponseEntity<JSONObject> createInquiry(JSONObject json);
    public ResponseEntity<JSONObject> readRecentByWriterInquiry(JSONObject json);
    public ResponseEntity<JSONObject> readAllByWriterInquiry(JSONObject json);
    public ResponseEntity<JSONObject> readAllInquiry();
    public ResponseEntity<JSONObject> updateInquiry(JSONObject json);
    public ResponseEntity<JSONObject> deleteInquiry(JSONObject json);

}
