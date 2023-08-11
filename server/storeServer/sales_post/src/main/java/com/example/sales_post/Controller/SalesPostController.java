package com.example.sales_post.Controller;

import com.example.sales_post.Service.InquiryServiceImpl;
import com.example.sales_post.Service.SalesPostServiceImpl;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class SalesPostController {
    private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
    private SalesPostServiceImpl salesPostServiceImpl;

    public SalesPostController(@Autowired SalesPostServiceImpl salesPostServiceImpl) {
        this.salesPostServiceImpl = salesPostServiceImpl;
    }

    @GetMapping("/recent-writer")
    public JSONObject getRecentInquiryByWriter(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.readRecentByWriter(jsonObject);
    }

    @GetMapping("/all-writer")
    public List<JSONObject> getAllInquiryByWriter(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.readAllByWriter(jsonObject);
    }

    @GetMapping("/all")
    public List<JSONObject> getAllInquiry(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.readAll();
    }

    @PostMapping("/")
    public JSONObject postInquiry(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.create(jsonObject);
    }

    @PutMapping("/")
    public JSONObject putInquiry(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.update(jsonObject);
    }

    @DeleteMapping("/")
    public JSONObject deleteInquiry(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.delete(jsonObject);
    }
}
