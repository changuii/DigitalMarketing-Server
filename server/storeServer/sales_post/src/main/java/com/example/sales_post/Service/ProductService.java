package com.example.sales_post.Service;

import com.example.sales_post.Entity.ProductEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProductService {
    JSONObject create(JSONObject jsonObject);
    JSONObject read(JSONObject jsonObject);
    JSONObject readAll();
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
    Map<String, Object> jsonToEntity(JSONObject jsonObject);
    JSONObject entityToJson(ProductEntity productEntity);
    JSONObject resultJsonObject(String result);
    JSONObject resultJsonObject(String result, List<JSONObject> jsonObjectList);
}
