package com.example.sales_post.DAO;

import com.example.sales_post.Entity.SalesPostEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface SalesPostDao {
    boolean create(SalesPostEntity salesPostEntity);
    SalesPostEntity readRecentByWriter(String postWriter);
    List<SalesPostEntity> readAllByWriter(String postWriter);
    List<SalesPostEntity> readAll();
    boolean update(SalesPostEntity salesPostEntity);
    boolean delete(Long postNumber);
}
