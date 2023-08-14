package com.example.sales_post.Controller;

import com.example.sales_post.Service.InquiryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

@RestController
public class InquiryController {
    private final static Logger logger = LoggerFactory.getLogger(InquiryController.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private InquiryServiceImpl inquiryService;

    public InquiryController(@Autowired InquiryServiceImpl inquiryService,
                             @Autowired RedisTemplate redisTemplate) {
        this.inquiryService = inquiryService;
        this.redisTemplate = redisTemplate;
    }

    public JSONObject readRecentInquiryByWriter(JSONObject jsonObject) {
        return inquiryService.readRecentByWriter(jsonObject);
    }

    public JSONObject readAllInquiryByWriter(JSONObject jsonObject) {
        return inquiryService.readAllByWriter(jsonObject);
    }

    public JSONObject readAllInquiry() {
        return inquiryService.readAll();
    }

    public JSONObject createInquiry(JSONObject jsonObject) {
        return inquiryService.create(jsonObject);
    }

    public JSONObject updateInquiry(JSONObject jsonObject) {
        return inquiryService.update(jsonObject);
    }

    public JSONObject deleteInquiry(JSONObject jsonObject) {
        return inquiryService.delete(jsonObject);
    }

    @KafkaListener(topics = "InquiryRequest", groupId = "foo")
    public void getMessage(JSONObject jsonObject) {
        logger.info("KafkaMessage: " + jsonObject.toString());
        actionControl(jsonObject);
    }

    public void sendMessage(String requestId, JSONObject jsonObject){
        HashOperations<String, String, JSONObject> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("InquiryResponse", requestId, jsonObject);
    }

    public void actionControl(JSONObject jsonObject){
        String action = (String) jsonObject.get("action");
        String requestId = (String) jsonObject.get("requestId");

        if ("inquiryCreate".equals(action)) {
            JSONObject resultJsonObject = createInquiry(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("inquiryReadRecentByWriter".equals(action)) {
            JSONObject resultJsonObject = readRecentInquiryByWriter(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("inquiryReadAllByWriter".equals(action)) {
            JSONObject resultJsonObject = readAllInquiryByWriter(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("inquiryReadAll".equals(action)) {
            JSONObject resultJsonObject = readAllInquiry();
            sendMessage(requestId, resultJsonObject);
        }
        if ("inquiryUpdate".equals(action)) {
            JSONObject resultJsonObject = updateInquiry(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("inquiryDelete".equals(action)) {
            JSONObject resultJsonObject = deleteInquiry(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
    }
}