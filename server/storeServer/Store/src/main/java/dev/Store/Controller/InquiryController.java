//package dev.Store.Controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.web.bind.annotation.*;
//import org.json.simple.JSONObject;
//
//@RestController
//public class InquiryController {
//    private final static Logger logger = LoggerFactory.getLogger(InquiryController.class);
//    private final RedisTemplate<String, Object> redisTemplate;
//    private InquiryService inquiryService;
//
//    public InquiryController(@Autowired InquiryService inquiryService,
//                             @Autowired RedisTemplate redisTemplate) {
//        this.inquiryService = inquiryService;
//        this.redisTemplate = redisTemplate;
//    }
//
//    public JSONObject create(JSONObject jsonObject) {
//        return inquiryService.create(jsonObject);
//    }
//
//    public JSONObject read(JSONObject jsonObject) {
//        return inquiryService.read(jsonObject);
//    }
//    public JSONObject readAll() {
//        return inquiryService.readAll();
//    }
//
//    public JSONObject update(JSONObject jsonObject) {
//        return inquiryService.update(jsonObject);
//    }
//
//    public JSONObject delete(JSONObject jsonObject) {
//        return inquiryService.delete(jsonObject);
//    }
//
//    @KafkaListener(topics = "InquiryRequest", groupId = "foo")
//    public void getMessage(JSONObject jsonObject) {
//        logger.info("KafkaMessage: " + jsonObject.toString());
//        actionControl(jsonObject);
//    }
//
//    public void sendMessage(String requestId, JSONObject jsonObject){
//        HashOperations<String, String, JSONObject> hashOperations = redisTemplate.opsForHash();
//        hashOperations.put("InquiryResponse", requestId, jsonObject);
//    }
//
//    public void actionControl(JSONObject jsonObject){
//        String action = (String) jsonObject.get("action");
//        String requestId = (String) jsonObject.get("requestId");
//
//        if ("inquiryCreate".equals(action)) {
//            JSONObject resultJsonObject = create(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("inquiryRead".equals(action)) {
//            JSONObject resultJsonObject = read(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("inquiryReadAll".equals(action)) {
//            JSONObject resultJsonObject = readAll();
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("inquiryUpdate".equals(action)) {
//            JSONObject resultJsonObject = update(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("inquiryDelete".equals(action)) {
//            JSONObject resultJsonObject = delete(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//    }
//}