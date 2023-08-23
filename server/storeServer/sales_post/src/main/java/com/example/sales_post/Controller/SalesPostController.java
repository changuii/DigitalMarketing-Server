package com.example.sales_post.Controller;

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

    @PostMapping("/create")
    public JSONObject createSalesPost(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.create(jsonObject);
    }

    @GetMapping("/read-recent-writer")
    public JSONObject readRecentSalesPostByWriter(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.readRecentByWriter(jsonObject);
    }

    @GetMapping("/read-all-writer")
    public JSONObject readAllSalesPostByWriter(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.readAllByWriter(jsonObject);
    }

    @GetMapping("/read-all")
    public JSONObject readAllSalesPost(){
        return salesPostServiceImpl.readAll();
    }

    @PutMapping("/update")
    public JSONObject updateSalesPost(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.update(jsonObject);
    }

    @DeleteMapping("/delete")
    public JSONObject deleteSalesPost(@RequestBody JSONObject jsonObject){
        return salesPostServiceImpl.delete(jsonObject);
    }
}
