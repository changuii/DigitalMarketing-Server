package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ReviewEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface ReviewDao {
    boolean create(ReviewEntity reviewEntity);
    ReviewEntity readRecentByWriter(Long postNumber, Long reviewNumber);
    List<ReviewEntity> readAllByWriter(Long postNumber, Long reviewNumber);
    List<ReviewEntity> readAll();
    boolean update(ReviewEntity reviewEntity);
    boolean delete(Long reviewNumber);
}
