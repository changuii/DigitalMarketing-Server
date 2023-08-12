package com.example.sales_post.Repository;

import com.example.sales_post.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> readAllByReviewWriter(String reviewWRiter);
    ReviewEntity findByReviewNumber(Long reviewNumber);
    boolean existsByReviewWriter(String reviewWriter);
    boolean existsByReviewNumber(Long reviewNumber);


}

//제목만 수정하거나 내용만 수정하거나 위치수정하는 경우 많음. 수정된것만 받는걸로