package com.example.sales_post.Service;


import com.example.sales_post.DAO.DAO;
import com.example.sales_post.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface SalesPostService {
    public SalesPostEntity createPost(JSONObject salesPost);
    public void readPost(long postNumber);
    public SalesPostEntity updatePost(JSONObject salesPost);
    public void deletePost(long postNumber);
}
