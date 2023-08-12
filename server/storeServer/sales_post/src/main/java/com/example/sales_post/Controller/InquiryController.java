package com.example.sales_post.Controller;

import com.example.sales_post.Service.InquiryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

import java.util.List;

@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
    private InquiryServiceImpl inquiryServiceImpl;

    public InquiryController(@Autowired InquiryServiceImpl inquiryServiceImpl) {
        this.inquiryServiceImpl = inquiryServiceImpl;
    }

    @GetMapping("/read-recent-writer")
    public JSONObject getRecentInquiryByWriter(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readRecentByWriter(jsonObject);
    }

    @GetMapping("/read-all-writer")
    public List<JSONObject> getAllInquiryByWriter(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readAllByWriter(jsonObject);
    }

    @GetMapping("/read-all")
    public List<JSONObject> getAllInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.readAll();
    }

    @PostMapping("/create")
    public JSONObject postInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.create(jsonObject);
    }

    @PutMapping("/update")
    public JSONObject putInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.update(jsonObject);
    }

    @DeleteMapping("/delete")
    public JSONObject deleteInquiry(@RequestBody JSONObject jsonObject){
        return inquiryServiceImpl.delete(jsonObject);
    }
}
