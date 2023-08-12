package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ReviewEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao {
    String create(ReviewEntity reviewEntity);
    List<ReviewEntity> readAllByWriter(String reviewWriter);
    List<ReviewEntity> readAll();
    String update(ReviewEntity reviewEntity);
    String delete(Long reviewNumber);
}