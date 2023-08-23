package dev.gateway.apigateway.service.impl;

import dev.gateway.apigateway.service.ProductService;
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
public class ProductServiceImpl implements ProductService {


    private final String TOPIC;
    private final String redisKey;
    private long requestId = 0;
    private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final KafkaService kafkaService;
    private final RedisTemplate<String, Object> redisTemplate;

    public ProductServiceImpl(
            @Autowired KafkaService kafkaService,
            @Autowired RedisTemplate redisTemplate,
            @Value("${redisKey.product}") String redisKey,
            @Value("${kafkaTOPIC.product}") String TOPIC
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

    public void print(){
        logger.info(this.TOPIC);
        logger.info(this.redisKey);
    }
    @Async
    @Override
    public ResponseEntity<JSONObject> createProduct(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "productCreate");


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
    public ResponseEntity<JSONObject> readProduct(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "productReadOne");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> readAllProduct() {
        String request = generateRequestID();

        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "productReadAll");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> updateProduct(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "productUpdate");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> deleteProduct(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "productDelete");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( response != null){
                break;
            }
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}
