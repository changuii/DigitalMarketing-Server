package dev.gateway.apigateway.service.impl;

import dev.gateway.apigateway.service.ReviewService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class ReviewServiceImpl implements ReviewService {
    private final String TOPIC = "ReviewRequest";
    private final String redisKey = "ReviewResponse";
    private long requestId = 0;
    private final KafkaService kafkaService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public ReviewServiceImpl(
            @Autowired KafkaService kafkaService,
            @Autowired RedisTemplate redisTemplate
    ){
        this.kafkaService = kafkaService;
        this.redisTemplate = redisTemplate;
    }

    public String generateRequestID(){
        this.requestId++;
        return Long.toString(this.requestId);
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> createReview(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "reviewCreate");


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
    public ResponseEntity<JSONObject> readAllByWriterReview(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "reviewReadAllByWriter");

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
    public ResponseEntity<JSONObject> readAllReview() {
        String request = generateRequestID();

        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "reviewReadAll");

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
    public ResponseEntity<JSONObject> updateReview(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "reviewUpdate");


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
    public ResponseEntity<JSONObject> deleteReview(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "reviewDelete");


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
