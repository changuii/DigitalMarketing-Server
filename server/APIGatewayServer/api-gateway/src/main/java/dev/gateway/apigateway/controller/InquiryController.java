package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.InquiryService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    private final static Logger logger = LoggerFactory.getLogger(InquiryController.class);
    private final InquiryService inquiryService;

    public InquiryController(
            @Autowired InquiryService inquiryService
    ){
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<JSONObject> createInquiry(
            @RequestBody JSONObject json
    ){

        return this.inquiryService.createInquiry(json);
    }

    @GetMapping("/writer/recent")
    public ResponseEntity<JSONObject> readRecentByWriterInquiry(
      @RequestBody JSONObject json
    ){
        return this.inquiryService.readRecentByWriterInquiry(json);

    }

    @GetMapping("/writer")
    public ResponseEntity<JSONObject> readAllByWriterInquiry(
            @RequestBody JSONObject json
    ){
        return this.inquiryService.readAllByWriterInquiry(json);


    }
    @GetMapping
    public ResponseEntity<JSONObject> readAllInquiry(){
        return this.inquiryService.readAllInquiry();

    }

    @PutMapping
    public ResponseEntity<JSONObject> updateInquiry(
            @RequestBody JSONObject json
    ){
        return this.inquiryService.updateInquiry(json);

    }

    @DeleteMapping
    public ResponseEntity<JSONObject> deleteInquiry(
            @RequestBody JSONObject json
    ){
        return this.inquiryService.deleteInquiry(json);
    }

}
