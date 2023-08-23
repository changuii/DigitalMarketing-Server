package dev.gateway.apigateway.service;

import org.json.simple.JSONObject;

public interface RedisService {
    public JSONObject getResponse(String redisKey, String hashKey);


}
