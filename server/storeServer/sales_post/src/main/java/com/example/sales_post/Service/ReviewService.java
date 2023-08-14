package com.example.sales_post.Service;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.ReviewEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ReviewService {
    JSONObject create(JSONObject jsonObject);
    JSONObject readAllByWriter(JSONObject jsonObject);
    JSONObject readAll();
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
    Map<String, Object> jsonToEntity(JSONObject jsonObject);
    JSONObject entityToJson(ReviewEntity reviewEntity);
    JSONObject resultJsonObject(String result);
    JSONObject resultJsonObject(String result, List<JSONObject> jsonObjectList);
}
