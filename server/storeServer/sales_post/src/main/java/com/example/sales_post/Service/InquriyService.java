package com.example.sales_post.Service;

import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@Service
public interface InquriyService {
    JSONObject create(JSONObject jsonObject);
    JSONObject readRecentByWriter(JSONObject jsonObject);
    JSONObject readAllByWriter(JSONObject jsonObject);
    JSONObject readAll();
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
    Map<String, Object> jsonToEntity(JSONObject jsonObject);
    JSONObject entityToJson(InquiryEntity inquiryEntity);
    JSONObject resultJsonObject(String result);
    JSONObject resultJsonObject(List<JSONObject> jsonObjectList);
}
