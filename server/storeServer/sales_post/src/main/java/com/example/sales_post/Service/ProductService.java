package com.example.sales_post.Service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    JSONObject createProduct(JSONObject jsonObject);
    JSONObject readRecentProduct(JSONObject jsonObject);
    List<JSONObject> readAllProduct(JSONObject jsonObject);
    JSONObject updateProduct(JSONObject jsonObject);
    JSONObject deleteProduct(JSONObject jsonObject);
}
