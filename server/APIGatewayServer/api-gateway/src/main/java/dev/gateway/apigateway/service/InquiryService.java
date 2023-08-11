package dev.gateway.apigateway.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface InquiryService {


    public ResponseEntity<JSONObject> createInquiry(JSONObject json, long postNum);
    public ResponseEntity<JSONObject> readRecentByWriterInquiry(JSONObject json, long postNum);
    public ResponseEntity<JSONObject> readAllByWriterInquiry(JSONObject json, long postNum);
    public ResponseEntity<JSONObject> readAllInquiry();
    public ResponseEntity<JSONObject> updateInquiry(JSONObject json, long postNum);
    public ResponseEntity<JSONObject> deleteInquiry(long inquiryNum);

}
