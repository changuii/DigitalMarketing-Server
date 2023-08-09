package com.example.sales_post.Service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesPostService {
    JSONObject createSalesPost(JSONObject jsonObject);
    JSONObject readRecentSalesPost(JSONObject jsonObject);
    List<JSONObject> readAllSalesPost(JSONObject jsonObject);
    JSONObject updateSalesPost(JSONObject jsonObject);
    JSONObject deleteSalesPost(JSONObject jsonObject);
}
