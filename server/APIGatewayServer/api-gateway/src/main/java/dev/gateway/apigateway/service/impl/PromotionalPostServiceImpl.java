package dev.gateway.apigateway.service.impl;


import dev.gateway.apigateway.service.PromotionalPostService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PromotionalPostServiceImpl implements PromotionalPostService {
    private final String TOPIC;
    private final String redisKey;
    private long requestId = 0;
    private final static Logger logger = LoggerFactory.getLogger(PromotionalPostServiceImpl.class);
    private final KafkaService kafkaService;
    private final RedisTemplate<String, Object> redisTemplate;

    public PromotionalPostServiceImpl(
            @Autowired KafkaService kafkaService,
            @Autowired RedisTemplate redisTemplate,
            @Value("${redisKey.promotionalpost}") String redisKey,
            @Value("${kafkaTOPIC.promotionalpost}") String TOPIC
    ){
        this.kafkaService = kafkaService;
        this.redisTemplate = redisTemplate;
        this.redisKey = redisKey;
        this.TOPIC = TOPIC;
    }

    public String generateRequestID(){
        this.requestId++;
        return Long.toString(this.requestId);
    }


    @Async
    @Override
    public ResponseEntity<JSONObject> createPromotionalPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostCreate");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(201).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> readAllPromotionalPost() {
        String request = generateRequestID();

        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "pmPostReadAll");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(201).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> updatePromotionalPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostUpdate");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(201).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> deletePromotionalPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostDelete");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(201).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}
