package com.example.sales_post.Service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    JSONObject createReview(JSONObject jsonObject);
    JSONObject readRecentReview(JSONObject jsonObject);
    List<JSONObject> readAllReview(JSONObject jsonObject);
    JSONObject updateReview(JSONObject jsonObject);
    JSONObject deleteReview(JSONObject jsonObject);
}
