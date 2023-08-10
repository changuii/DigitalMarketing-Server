package com.example.sales_post.Service;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.ProductEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    JSONObject create(JSONObject jsonObject);
    JSONObject readOne(JSONObject jsonObject);
    List<JSONObject> readAll(JSONObject jsonObject);
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
    ProductEntity jsonToEntity(JSONObject jsonObject);
    JSONObject resultJsonObject(boolean result);
    JSONObject resultJsonObject(boolean result, ProductEntity productEntity);
}
