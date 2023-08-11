package com.example.sales_post.Repository;

import com.example.sales_post.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    @Query(value = "SELECT * FROM review WHERE reviewAuthor = :reviewauthor ORDER BY reviewDate",nativeQuery = true) //작성자의 모든 리뷰 날짜 오름차순으로 정렬해서 보여주기
    List<ReviewEntity> readAllByReviewAuthor(String reviewAuthor);
    boolean existsByReviewAuthor(String reviewAuthor);
    boolean existsByReviewNumber(Long reviewNumber);
}

//제목만 수정하거나 내용만 수정하거나 위치수정하는 경우 많음. 수정된것만 받는걸로