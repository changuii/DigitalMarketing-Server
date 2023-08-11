package dev.gateway.apigateway.service.impl;

import dev.gateway.apigateway.service.SalesPostService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class SalesPostServiceImpl implements SalesPostService {
    private final String TOPIC = "SalesPostRequest";
    private final String redisKey = "SalesPostResponse";
    private long requestId = 0;
    private final KafkaService kafkaService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(SalesPostServiceImpl.class);

    public SalesPostServiceImpl(
            @Autowired KafkaService kafkaService,
            @Autowired RedisTemplate redisTemplate
    ){
        this.kafkaService=kafkaService;
        this.redisTemplate=redisTemplate;
    }

    public String generateRequestID(){
        this.requestId++;
        return Long.toString(requestId);
    }

    @Override
    public ResponseEntity<JSONObject> createSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostCreate");

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

    @Override
    public ResponseEntity<JSONObject> readRecentByWriterSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostReadRecentByWriter”");

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

    @Override
    public ResponseEntity<JSONObject> readAllByWriterSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostReadAllByWriter");

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

    @Override
    public ResponseEntity<JSONObject> readAllSalesPost() {
        String request = generateRequestID();

        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "salesPostReadAll");

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

    @Override
    public ResponseEntity<JSONObject> updateSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostUpdate");

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

    @Override
    public ResponseEntity<JSONObject> deleteSalesPost(long salesPostNum) {
        String request = generateRequestID();

        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "salesPostDelete");

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
