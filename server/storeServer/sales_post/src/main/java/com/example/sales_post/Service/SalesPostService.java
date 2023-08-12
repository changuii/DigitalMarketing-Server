package com.example.sales_post.Service;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesPostService {
    JSONObject create(JSONObject jsonObject);
    JSONObject readRecentByWriter(JSONObject jsonObject);
    List<JSONObject> readAllByWriter(JSONObject jsonObject);
    List<JSONObject> readAll();
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
    SalesPostEntity jsonToEntity(JSONObject jsonObject);
    JSONObject entityToJson(SalesPostEntity salesPostEntity);
    JSONObject resultJsonObject(String result);
}
