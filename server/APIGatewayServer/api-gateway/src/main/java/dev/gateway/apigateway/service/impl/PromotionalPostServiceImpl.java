package dev.gateway.apigateway.service.impl;

import dev.gateway.apigateway.repository.UserRepository;
import dev.gateway.apigateway.service.ImageService;
import dev.gateway.apigateway.service.PromotionalPostService;
import dev.gateway.apigateway.service.RedisService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PromotionalPostServiceImpl implements PromotionalPostService {
    private final String TOPIC;
    private final String redisKey;
    private long requestId = 0;
    private final static Logger logger = LoggerFactory.getLogger(PromotionalPostServiceImpl.class);
    private final KafkaService kafkaService;
    private final ImageService imageService;
    private final RedisService redisService;

    public PromotionalPostServiceImpl(
            @Autowired KafkaService kafkaService,
            @Autowired RedisService redisService,
            @Autowired ImageService imageService,
            @Autowired UserRepository userRepository,
            @Value("${redisKey.promotionalpost}") String redisKey,
            @Value("${kafkaTOPIC.promotionalpost}") String TOPIC
    ){
        this.kafkaService = kafkaService;
        this.imageService = imageService;
        this.redisService = redisService;
        this.redisKey = redisKey;
        this.TOPIC = TOPIC;
    }

    public String generateRequestID(){
        if(this.requestId == 10000){
            requestId = 0;
        }
        this.requestId++;
        return Long.toString(this.requestId);
    }


    @Async
    @Override
    public ResponseEntity<JSONObject> createPromotionalPost(JSONObject json, List<MultipartFile> img) throws IOException {
        String request = generateRequestID();

        List<String> images = this.imageService.saveImages(img);

        json.put("requestId", request);
        json.put("action", "pmPostCreate");
        json.put("pmMainImage", images.get(0));
        images.remove(0);
        json.put("pmPostPictures", images);

        kafkaService.sendMessage(json, TOPIC);
        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(201).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
    @Async
    @Override
    public ResponseEntity<JSONObject> readPromotionalPost(
            JSONObject json
    ) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostRead");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
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

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
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

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
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

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> categoryReadAllPromotionalPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostReadCategory");


        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Async
    @Override
    public ResponseEntity<JSONObject> tagReadAllPromotionalPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostReadTag");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }


    @Async
    @Override
    public ResponseEntity<JSONObject> createComment(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmCommentCreate");

        kafkaService.sendMessage(json, TOPIC);
        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(201).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public ResponseEntity<JSONObject> updateComment(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmCommentUpdate");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public ResponseEntity<JSONObject> deleteComment(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmCommentDelete");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public ResponseEntity<JSONObject> postLike(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostLike");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public ResponseEntity<JSONObject> commentLike(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmCommentLike");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public ResponseEntity<JSONObject> readBySalesPostNumber(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "pmPostsFromSalesPost");


        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);


        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}
