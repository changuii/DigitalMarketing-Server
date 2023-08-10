package com.example.sales_post.DAO;

import org.json.simple.JSONObject;

import java.util.List;

public interface ProductDao {
    JSONObject create(JSONObject jsonObject);
    JSONObject readOne(JSONObject jsonObject);
    List<JSONObject> readAll(JSONObject jsonObject);
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
}
