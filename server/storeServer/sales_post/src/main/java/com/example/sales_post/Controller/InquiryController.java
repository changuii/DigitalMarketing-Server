package com.example.sales_post.Controller;

import com.example.sales_post.Service.InquiryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

import java.util.List;

@RestController
@RequestMapping("/Store")
public class InquiryController {
    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
    private InquiryServiceImpl inquiryServiceImpl;

    public InquiryController(@Autowired InquiryServiceImpl inquiryServiceImpl) {
        this.inquiryServiceImpl = inquiryServiceImpl;
    }

    @GetMapping("/recent")
    public JSONObject getRecentInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readRecentInquiry(jsonObject);
    }

    @GetMapping("/all")
    public List<JSONObject> getAllInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readAllInquiry(jsonObject);
    }

    @PostMapping("/")
    public JSONObject postInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.createInquiry(jsonObject);
    }

    @PutMapping("/")
    public JSONObject putInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.updateInquiry(jsonObject);
    }

    @DeleteMapping("/")
    public JSONObject deleteInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.deleteInquiry(jsonObject);
    }
}
