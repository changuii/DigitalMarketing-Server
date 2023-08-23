package com.example.sales_post.DAO;

import com.example.sales_post.Entity.SalesPostEntity;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public interface SalesPostDao {
    String create(SalesPostEntity salesPostEntity);
    Map<String, Object> read(Long postNumber);
    Map<String, Object> readRecentByWriter(String postWriter);
    Map<String, Object> readAllByWriter(String postWriter);
    Map<String, Object> readAll();
    String update(SalesPostEntity salesPostEntity);
    String delete(Long postNumber);
}