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

    @PostMapping("/{postNum}")
    public ResponseEntity<JSONObject> createInquiry(
            @RequestBody JSONObject json,
            @PathVariable("postNum") long postNum
    ){

        return this.inquiryService.createInquiry(json, postNum);
    }

    @GetMapping("/{postNum}/recent")
    public ResponseEntity<JSONObject> readRecentByWriterInquiry(
      @PathVariable("postNum") long postNum,
      @RequestBody JSONObject json
    ){
        return this.inquiryService.readRecentByWriterInquiry(json, postNum);

    }

    @GetMapping("/{postNum}")
    public ResponseEntity<JSONObject> readAllByWriterInquiry(
            @PathVariable("postNum") long postNum,
            @RequestBody JSONObject json
    ){
        return this.inquiryService.readAllByWriterInquiry(json, postNum);


    }
    @GetMapping
    public ResponseEntity<JSONObject> readAllInquiry(){
        return this.inquiryService.readAllInquiry();

    }

    @PutMapping("/{postNum}")
    public ResponseEntity<JSONObject> updateInquiry(
            @RequestBody JSONObject json,
            @PathVariable("postNum") long postNum
    ){
        return this.inquiryService.updateInquiry(json, postNum);

    }

    @DeleteMapping("/{inquiryNum}")
    public ResponseEntity<JSONObject> deleteInquiry(
            @PathVariable("inquiryNum") long inquiryNum
    ){
        return this.inquiryService.deleteInquiry(inquiryNum);
    }

}
