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

    @GetMapping("/")
    public JSONObject getInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readInquiry((String) jsonObject.get("author"));
    }
    @GetMapping("/recent")
    public JSONObject getRecentInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readRecentInquiry((String) jsonObject.get("author"));
    }

    @GetMapping("/all")
    public List<JSONObject> getAllInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readAllInquiry((String) jsonObject.get("author"));
    }

    @PostMapping("/")
    public void postInquiry(@RequestBody JSONObject jsonObject){
        inquiryServiceImpl.createInquiry(jsonObject);
    }

    @PutMapping("/")
    public void putInquiry(@RequestBody JSONObject jsonObject){
        inquiryServiceImpl.updateInquiry(jsonObject);
    }

    @DeleteMapping("/")
    public void deleteInquiry(@RequestBody JSONObject jsonObject){
        String inquiryNumberStr = (String) jsonObject.get("inquiryNumber");
        Long inquiryNumber = Long.valueOf(inquiryNumberStr);
        inquiryServiceImpl.deletePost(inquiryNumber);
    }
}
