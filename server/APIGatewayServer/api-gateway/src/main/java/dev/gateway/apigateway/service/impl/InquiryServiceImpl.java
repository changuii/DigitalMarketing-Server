package dev.gateway.apigateway.service.impl;


import dev.gateway.apigateway.service.InquiryService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InquiryServiceImpl implements InquiryService {

    private final String redisKey = "InquiryResponse";
    private final String TOPIC = "InquiryRequest";
    private long requestId = 0;
    private final KafkaService kafkaService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(InquiryServiceImpl.class);

    public InquiryServiceImpl(
            @Autowired RedisTemplate redisTemplate,
            @Autowired KafkaService kafkaService
    ){
        this.kafkaService = kafkaService;
        this.redisTemplate = redisTemplate;
    }

    public long generateRequestId(){
        this.requestId++;
        this.logger.info("requestID : "+requestId);
        return this.requestId;
    }


    @Async
    @Override
    public ResponseEntity<JSONObject> createInquiry(JSONObject json, long postNum) {
        String request = Long.toString(this.generateRequestId());

        json.put("requestId", request);
        json.put("action", "inquiryCreate");
        json.put("salesPostNumber", postNum);


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

    @Override
    public ResponseEntity<JSONObject> readRecentByWriterInquiry(JSONObject json, long postNum) {
        String request = Long.toString(this.generateRequestId());


        json.put("requestId", request);
        json.put("action", "inquiryReadRecentByWriter");
        json.put("salesPostNumber", postNum);


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

    @Override
    public ResponseEntity<JSONObject> readAllByWriterInquiry(JSONObject json, long postNum) {
        String request = Long.toString(this.generateRequestId());

        json.put("requestId", request);
        json.put("action", "inquiryReadAllByWriter");
        json.put("salesPostNumber", postNum);

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

    @Override
    public ResponseEntity<JSONObject> readAllInquiry() {
        String request = Long.toString(this.generateRequestId());


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

    @Override
    public ResponseEntity<JSONObject> updateInquiry(JSONObject json,long postNum) {
        String request = Long.toString(this.generateRequestId());

        json.put("requestId", request);
        json.put("action", "inquiryUpdate");
        json.put("salesPostNumber", postNum);

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

    @Override
    public ResponseEntity<JSONObject> deleteInquiry(long inquiryNum) {
        String request = Long.toString(this.generateRequestId());


        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "inquiryDelete");
        json.put("inquiryNumber", inquiryNum);


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
