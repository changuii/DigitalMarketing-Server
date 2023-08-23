package dev.gateway.apigateway.service.impl;

import dev.gateway.apigateway.Entity.UserEntity;
import dev.gateway.apigateway.repository.UserRepository;
import dev.gateway.apigateway.service.ImageService;
import dev.gateway.apigateway.service.PromotionalPostService;
import dev.gateway.apigateway.service.SalesPostService;
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
import java.util.ArrayList;
import java.util.List;


@Service
public class SalesPostServiceImpl implements SalesPostService {
    private final String TOPIC;
    private final String redisKey;
    private long requestId = 0;
    private final KafkaService kafkaService;
    private final RedisService redisService;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final PromotionalPostService promotionalPostService;
    private final static Logger logger = LoggerFactory.getLogger(SalesPostServiceImpl.class);

    public SalesPostServiceImpl(
            @Autowired KafkaService kafkaService,
            @Autowired RedisService redisService,
            @Autowired ImageService imageService,
            @Autowired PromotionalPostService promotionalPostService,
            @Autowired UserRepository userRepository,
            @Value("${redisKey.salespost}") String redisKey,
            @Value("${kafkaTOPIC.salespost}") String TOPIC
    ){
        this.kafkaService=kafkaService;
        this.userRepository = userRepository;
        this.promotionalPostService = promotionalPostService;
        this.redisService = redisService;
        this.imageService = imageService;
        this.redisKey = redisKey;
        this.TOPIC = TOPIC;
    }

    public String generateRequestID(){
        if(this.requestId == 10000){
            this.requestId = 0;
        }
        this.requestId++;
        return Long.toString(requestId);
    }

    public String[] likesToString(String likes){
        String[] like = likes.split(",");
        return like;
    }

    @Async
    @Override
    public ResponseEntity<JSONObject> createSalesPost(JSONObject json, List<MultipartFile> image) throws IOException {
        String request = generateRequestID();
        List<String> url = this.imageService.saveImages(image);

        json.put("requestId", request);
        json.put("action", "salesPostCreate");
        json.put("mainImage", url.get(0));
        url.remove(0);
        json.put("descImages", url);

        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);

        if(response.get("result").equals("success")){
            return ResponseEntity.status(201).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Override
    public ResponseEntity<JSONObject> readBySalesPostNumber(long id) {
        String request = generateRequestID();


        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "salesPostRead");
        json.put("salesPostNumber", String.valueOf(id));

        JSONObject promotion = new JSONObject();
        promotion.put("salesPostNumber", id);



        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);

        ResponseEntity<JSONObject> promotionResponse = this.promotionalPostService.readBySalesPostNumber(json);
        if(promotionResponse.getBody().get("result").toString().equals("Post not found")){
            response.put("reviewData", new ArrayList<>());
        }else{
            JSONObject promotionData = (JSONObject) promotionResponse.getBody();
            response.put("reviewData", promotionData.get("data"));
        }

        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            response.remove("reviewData");
            return ResponseEntity.badRequest().body(response);
        }



    }

    @Async
    @Override
    public ResponseEntity<JSONObject> readRecentByWriterSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostReadRecentByWriter");

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
    public ResponseEntity<JSONObject> readAllByWriterSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostReadAllByWriter");

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
    public ResponseEntity<JSONObject> readAllSalesPost() {
        String request = generateRequestID();

        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "salesPostReadAll");

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
    public ResponseEntity<JSONObject> updateSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostUpdate");

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
    public ResponseEntity<JSONObject> deleteSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostDelete");

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
    public ResponseEntity<JSONObject> postLikeSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostLike");

        UserEntity userEntity = this.userRepository.getByUid(json.get("email").toString());
        logger.info(userEntity.toString());
        if(json.get("disLike").toString().equals("Like")){
            if (userEntity.getLikes() == null) {
                userEntity.setLikes(json.get("salesPostNumber").toString());
                this.userRepository.updateLikesByUid(userEntity.getUid(), userEntity.getLikes());
            } else if(userEntity.getLikes().equals("")){
                userEntity.setLikes(json.get("salesPostNumber").toString());
                this.userRepository.updateLikesByUid(userEntity.getUid(), userEntity.getLikes());
            }
            else {
                String likes = userEntity.getLikes();
                likes = likes + "," + json.get("salesPostNumber").toString();
                userEntity.setLikes(likes);
                this.userRepository.updateLikesByUid(userEntity.getUid(), userEntity.getLikes());
            }
        }else{
            String target = "";
            String[] likes = this.likesToString(userEntity.getLikes());
            for(String like : likes){
                logger.info(like);
                if(!like.equals(json.get("salesPostNumber").toString())){
                    target = ","+target+like;
                }
            }
            if(!target.equals("")){
                target = target.substring(1, target.length());

            }
            this.userRepository.updateLikesByUid(userEntity.getUid(), target);
        }

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
    public ResponseEntity<JSONObject> hitCountSalesPost(JSONObject json) {
        String request = generateRequestID();

        json.put("requestId", request);
        json.put("action", "salesPostHitCount");

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
    public ResponseEntity<JSONObject> readAllByCategorySalesPost(String category) {
        String request = generateRequestID();


        JSONObject json = new JSONObject();
        json.put("requestId", request);
        json.put("action", "salesPostReadAllByCategory");
        json.put("category", category);

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
        json.put("action", "salesPostCommentCreate");

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
        json.put("action", "salesPostCommentDelete");

        kafkaService.sendMessage(json, TOPIC);

        JSONObject response = this.redisService.getResponse(redisKey, request);

        if(response.get("result").equals("success")){
            return ResponseEntity.status(200).body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}
