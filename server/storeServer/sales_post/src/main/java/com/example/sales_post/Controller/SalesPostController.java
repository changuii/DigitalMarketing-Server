package com.example.sales_post.Controller;

import com.example.sales_post.Service.SalesPostService;
import com.example.sales_post.Service.SalesPostServiceImpl;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
public class SalesPostController {
    private SalesPostService salesPostService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(SalesPostController.class);

    public SalesPostController(@Autowired SalesPostService salesPostService,
                               @Autowired RedisTemplate redisTemplate) {
        this.salesPostService = salesPostService;
        this.redisTemplate = redisTemplate;
    }

    public JSONObject createSalesPost(JSONObject jsonObject){return salesPostService.create(jsonObject);}

    public JSONObject readRecentSalesPostByWriter(JSONObject jsonObject){return salesPostService.readRecentByWriter(jsonObject);}

    public JSONObject readAllSalesPostByWriter(JSONObject jsonObject){return salesPostService.readAllByWriter(jsonObject);}

    public JSONObject readAllSalesPost(){
        return salesPostService.readAll();
    }

    public JSONObject updateSalesPost(JSONObject jsonObject){return salesPostService.update(jsonObject);}

    public JSONObject deleteSalesPost(JSONObject jsonObject){return salesPostService.delete(jsonObject);}

//    @KafkaListener(topics = "SalesPostRequest", groupId = "foo")
//    public void getMessage(JSONObject jsonObject){
//        logger.info("KafkaMessage: " + jsonObject.toString());
//        actionControl(jsonObject);
//    }
//
//    public void sendMessage(String requestId, JSONObject jsonObject){
//        HashOperations<String, String, JSONObject> hashOperations = redisTemplate.opsForHash();
//        hashOperations.put("SalesPostResponse", requestId, jsonObject);
//    }
//
//    public void actionControl(JSONObject jsonObject){
//        String action = (String) jsonObject.get("action");
//        String requestId = (String) jsonObject.get("requestId");
//
//        if ("salesPostCreate".equals(action)) {
//            JSONObject resultJsonObject = createSalesPost(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("salesPostReadRecentByWriter".equals(action)) {
//            JSONObject resultJsonObject = readRecentSalesPostByWriter(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("salesPostReadAllByWriter".equals(action)) {
//            JSONObject resultJsonObject = readAllSalesPostByWriter(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("salesPostReadAll".equals(action)) {
//            JSONObject resultJsonObject = readAllSalesPost();
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("salesPostUpdate".equals(action)) {
//            JSONObject resultJsonObject = updateSalesPost(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("salesPostDelete".equals(action)) {
//            JSONObject resultJsonObject = deleteSalesPost(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//    }
}
