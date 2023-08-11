package com.example.sales_post.Repository;

import com.example.sales_post.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(value = "SELECT * FROM review WHERE reviewauthor = :reviewauthor",nativeQuery = true)
    List<ReviewEntity> readAllByReviewAuthor(String reviewAuthor);
    boolean existsByReviewAuthor(String reviewAuthor);
    boolean existsByReviewNumber(Long reviewNumber);
}
