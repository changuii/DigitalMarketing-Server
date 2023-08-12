package dev.gateway.apigateway.service.impl;


import dev.gateway.apigateway.service.InquiryService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InquiryServiceImpl implements InquiryService {

    private final String redisKey;
    private final String TOPIC;
    private long requestId = 0;
    private final KafkaService kafkaService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(InquiryServiceImpl.class);

    public InquiryServiceImpl(
            @Autowired RedisTemplate redisTemplate,
            @Autowired KafkaService kafkaService,
            @Value("${redisKey.inquiry}") String redisKey,
            @Value("${kafkaTOPIC.inquiry}") String TOPIC
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
    public ResponseEntity<JSONObject> createInquiry(JSONObject json) {
        String request = this.generateRequestID();

        json.put("requestId", request);
        json.put("action", "inquiryCreate");

        kafkaService.sendMessage(json, TOPIC);
        JSONObject response = new JSONObject();
        logger.info("requestID : "+ requestId + "request"+request);
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
    public ResponseEntity<JSONObject> readRecentByWriterInquiry(JSONObject json) {
        String request = this.generateRequestID();


        json.put("requestId", request);
        json.put("action", "inquiryReadRecentByWriter");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( !(response.isEmpty())){
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
    public ResponseEntity<JSONObject> readAllByWriterInquiry(JSONObject json) {
        String request = this.generateRequestID();

        json.put("requestId", request);
        json.put("action", "inquiryReadAllByWriter");

        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( !(response.isEmpty())){
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
    public ResponseEntity<JSONObject> readAllInquiry() {
        String request = this.generateRequestID();


        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "inquiryReadAll");
        json.put("inquiryNumber", "null");
        json.put("inquiryWriter", "all");

        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( !(response.isEmpty())){
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
    public ResponseEntity<JSONObject> updateInquiry(JSONObject json) {
        String request = this.generateRequestID();

        json.put("requestId", request);
        json.put("action", "inquiryUpdate");

        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( !(response.isEmpty())){
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
    public ResponseEntity<JSONObject> deleteInquiry(JSONObject json) {
        String request = this.generateRequestID();

        json.put("requestId", request);
        json.put("action", "inquiryDelete");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(this.redisKey, request);
            if( !(response.isEmpty())){
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
