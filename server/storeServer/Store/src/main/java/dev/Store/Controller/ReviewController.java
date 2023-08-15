//package dev.Store.Controller;
//
//
//import org.json.simple.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class ReviewController {
//    private final ReviewService reviewService;
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final static Logger logger = LoggerFactory.getLogger(ReviewController.class);
//
//    public ReviewController(@Autowired ReviewService reviewService,
//                            @Autowired RedisTemplate redisTemplate) {
//        this.reviewService = reviewService;
//        this.redisTemplate = redisTemplate;
//    }
//
//    public JSONObject create(JSONObject jsonObject){return reviewService.create(jsonObject);}
//    public JSONObject read(JSONObject jsonObject){return reviewService.read(jsonObject);}
//
//    public JSONObject readAll(){return reviewService.readAll();}
//
//    public JSONObject update(JSONObject jsonObject){return reviewService.update(jsonObject);}
//
//    public JSONObject delete(JSONObject jsonObject){return reviewService.delete(jsonObject);}
//
//    @KafkaListener(topics = "ReviewRequest", groupId = "foo")
//    public void getMessage(JSONObject jsonObject){
//        logger.info("KafkaMessage: " + jsonObject.toString());
//        actionControl(jsonObject);
//    }
//
//    public void sendMessage(String requestId, JSONObject jsonObject){
//        HashOperations<String, String, JSONObject> hashOperations = redisTemplate.opsForHash();
//        hashOperations.put("ReviewResponse", requestId, jsonObject);
//    }
//
//    public void actionControl(JSONObject jsonObject){
//        String action = (String) jsonObject.get("action");
//        String requestId = (String) jsonObject.get("requestId");
//
//        if ("reviewCreate".equals(action)) {
//            JSONObject resultJsonObject = create(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("reviewRead".equals(action)) {
//            JSONObject resultJsonObject = read(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("reviewReadAll".equals(action)) {
//            JSONObject resultJsonObject = readAll();
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("reviewUpdate".equals(action)) {
//            JSONObject resultJsonObject = update(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("reviewDelete".equals(action)) {
//            JSONObject resultJsonObject = delete(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//    }
//}
