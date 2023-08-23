package dev.gateway.apigateway.service.impl;

import dev.gateway.apigateway.service.RedisService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    private final static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    private final RedisTemplate<String, Object> redisTemplate;


    public RedisServiceImpl(
            @Autowired RedisTemplate redisTemplate
    ){
        this.redisTemplate = redisTemplate;
    }

    @Async
    @Override
    public JSONObject getResponse(String redisKey, String hashKey) {
        JSONObject response = new JSONObject();
        while(true){
            response = (JSONObject) redisTemplate.opsForHash().get(redisKey, hashKey);
            if( response != null){
                logger.info("Request ID : {} Response : {}", hashKey, response.toJSONString());
                redisTemplate.opsForHash().delete(redisKey, hashKey);
                break;
            }
        }


        return response;
    }
}
