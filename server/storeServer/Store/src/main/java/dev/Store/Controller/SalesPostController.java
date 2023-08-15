package dev.Store.Controller;

import dev.Store.Service.SalesPostService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesPostController {
    private final SalesPostService salesPostService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(SalesPostController.class);

    public SalesPostController(@Autowired SalesPostService salesPostService,
                               @Autowired RedisTemplate redisTemplate) {
        this.salesPostService = salesPostService;
        this.redisTemplate = redisTemplate;
    }
    public JSONObject create(JSONObject jsonObject){return salesPostService.create(jsonObject);}
    public JSONObject read(JSONObject jsonObject){return salesPostService.read(jsonObject);}
    public JSONObject readAllByCategory(@RequestBody JSONObject jsonObject){return salesPostService.readAllByCategory(jsonObject);}
    public JSONObject readAll(){return salesPostService.readAll();}
    public JSONObject update(JSONObject jsonObject){return salesPostService.update(jsonObject);}
    public JSONObject postLikeUpdate(JSONObject jsonObject){return salesPostService.postLikeUpdate(jsonObject);}
    public JSONObject postHitCountUpdate(JSONObject jsonObject){return salesPostService.postHitCountUpdate(jsonObject);}
    public JSONObject delete(JSONObject jsonObject){return salesPostService.delete(jsonObject);}

    @KafkaListener(topics = "SalesPostRequest", groupId = "SalesPost")
    public void getMessage(JSONObject jsonObject){
        logger.info("KafkaMessage: " + jsonObject.toString());
        actionControl(jsonObject);
    }

    public void sendMessage(String requestId, JSONObject jsonObject){
        HashOperations<String, String, JSONObject> hashOperations = redisTemplate.opsForHash();
        logger.info("[SendMessage: " + jsonObject.toString() + "]");
        hashOperations.put("SalesPostResponse", requestId, jsonObject);
    }

    public void actionControl(JSONObject jsonObject){
        String action = (String) jsonObject.get("action");
        String requestId = (String) jsonObject.get("requestId");
        logger.info("[Action: " + action + ", RequestId: " + requestId + "]");
        if ("salesPostCreate".equals(action)) {
            JSONObject resultJsonObject = create(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("salesPostRead".equals(action)) {
            JSONObject resultJsonObject = read(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("salesPostReadAllByCategory".equals(action)) {
            JSONObject resultJsonObject = readAllByCategory(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("salesPostReadAll".equals(action)) {
            JSONObject resultJsonObject = readAll();
            sendMessage(requestId, resultJsonObject);
        }
        if ("salesPostUpdate".equals(action)) {
            JSONObject resultJsonObject = update(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("salesPostLike".equals(action)) {
            JSONObject resultJsonObject = postLikeUpdate(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("salesPostHitCount".equals(action)) {
            JSONObject resultJsonObject = postHitCountUpdate(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
        if ("salesPostDelete".equals(action)) {
            JSONObject resultJsonObject = delete(jsonObject);
            sendMessage(requestId, resultJsonObject);
        }
    }
}