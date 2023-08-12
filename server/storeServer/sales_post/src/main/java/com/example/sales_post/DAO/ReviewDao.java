package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ReviewEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReviewDao {
    String create(ReviewEntity reviewEntity);
    Map<String, Object> readAllByWriter(String reviewWriter);
    Map<String, Object> readAll();
    String update(ReviewEntity reviewEntity);
    String delete(Long reviewNumber);
}