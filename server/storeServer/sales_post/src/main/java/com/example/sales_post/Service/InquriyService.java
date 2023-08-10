package com.example.sales_post.Service;

import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public interface InquriyService {
    JSONObject create(JSONObject jsonObject);
    JSONObject readRecentByWriter(JSONObject jsonObject);
    List<JSONObject> readAllByWriter(JSONObject jsonObject);
    List<JSONObject> readAll();
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
    InquiryEntity jsonToEntity(JSONObject jsonObject);
    JSONObject resultJsonObject(boolean result);
    JSONObject resultJsonObject(boolean result, InquiryEntity inquiryEntity);
}
