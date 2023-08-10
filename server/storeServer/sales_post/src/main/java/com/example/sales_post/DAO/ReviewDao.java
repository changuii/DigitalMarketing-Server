package com.example.sales_post.DAO;

import org.json.simple.JSONObject;

import java.util.List;

public interface ReviewDao {
    JSONObject create(JSONObject jsonObject);
    JSONObject readRecentByWriter(JSONObject jsonObject);
    List<JSONObject> readAllByWriter(JSONObject jsonObject);
    List<JSONObject> readAll();
    JSONObject update(JSONObject jsonObject);
    JSONObject delete(JSONObject jsonObject);
}
