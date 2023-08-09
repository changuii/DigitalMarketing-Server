package com.example.sales_post.Service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InquriyService {
    JSONObject createInquiry(JSONObject jsonObject);
    JSONObject readRecentInquiry(JSONObject jsonObject);
    List<JSONObject> readAllInquiry(JSONObject jsonObject);
    JSONObject updateInquiry(JSONObject jsonObject);
    JSONObject deleteInquiry(JSONObject jsonObject);
}
