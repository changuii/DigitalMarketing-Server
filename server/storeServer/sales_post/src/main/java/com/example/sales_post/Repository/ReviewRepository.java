package com.example.sales_post.Repository;

import com.example.sales_post.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByReviewWriter(String reviewWRiter);
    ReviewEntity findByReviewNumber(Long reviewNumber);
    boolean existsByReviewNumber(Long reviewNumber);
}