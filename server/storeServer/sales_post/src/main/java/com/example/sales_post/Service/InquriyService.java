package com.example.sales_post.Service;

import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface InquriyService {
    public boolean createInquiry(JSONObject jsonObject);
    public JSONObject readRecentInquiry(String author);
    public boolean updateInquiry(JSONObject jsonObject);
    public void deletePost(Long id);
}
