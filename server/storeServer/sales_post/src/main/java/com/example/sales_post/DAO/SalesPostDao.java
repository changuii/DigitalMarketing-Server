package com.example.sales_post.DAO;

import com.example.sales_post.Entity.SalesPostEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface SalesPostDao {
    String create(SalesPostEntity salesPostEntity);
    SalesPostEntity read(Long postNumber);
    SalesPostEntity readRecentByWriter(String postWriter);
    List<SalesPostEntity> readAllByWriter(String postWriter);
    List<SalesPostEntity> readAll();
    String update(SalesPostEntity salesPostEntity);
    String delete(Long postNumber);
}