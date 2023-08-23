package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ReviewEntity;
import com.example.sales_post.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReviewDaoImpl implements ReviewDao{
    private final ReviewRepository reviewRepository;

    public ReviewDaoImpl(@Autowired ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public String create(ReviewEntity reviewEntity) {
        reviewRepository.save(reviewEntity);

        if (reviewRepository.existsByReviewNumber(reviewEntity.getReviewNumber())) {
            return "success";
        } else {
            return "Error: Failed to create review";
        }
    }

    @Override
    public Map<String, Object> readAllByWriter(String reviewWriter) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findAllByReviewWriter(reviewWriter);
        Map<String, Object> result = new HashMap<>();
        if(reviewEntityList.isEmpty()){
            result.put("result", "Error: No reviews found fot writer");
        } else{
            result.put("data", reviewEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public Map<String, Object> readAll() {
        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        if(reviewEntityList.isEmpty()){
            result.put("result", "Error: No reviews found");
        } else{
            result.put("data", reviewEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public String update(ReviewEntity reviewEntity) {
        if (reviewRepository.existsByReviewNumber(reviewEntity.getReviewNumber())) {
            ReviewEntity oldReviewEntity = reviewRepository.findByReviewNumber(reviewEntity.getReviewNumber());
            reviewEntity.setReviewWriter(Optional.ofNullable(reviewEntity.getReviewWriter()).orElse(oldReviewEntity.getReviewWriter()));
            reviewEntity.setReviewContents(Optional.ofNullable(reviewEntity.getReviewContents()).orElse(oldReviewEntity.getReviewContents()));
            reviewEntity.setReviewDate(Optional.ofNullable(reviewEntity.getReviewDate()).orElse(oldReviewEntity.getReviewDate()));
            reviewEntity.setReviewLike(Optional.ofNullable(reviewEntity.getReviewLike()).orElse(oldReviewEntity.getReviewLike()));
            reviewEntity.setReviewStarRating(Optional.ofNullable(reviewEntity.getReviewStarRating()).orElse(oldReviewEntity.getReviewStarRating()));
            reviewEntity.setSalesPostEntity(Optional.ofNullable(reviewEntity.getSalesPostEntity()).orElse(oldReviewEntity.getSalesPostEntity()));
            reviewRepository.save(reviewEntity);
            return "success";
        } else{
            return "Error: Review not found";
        }
    }

    @Override
    public String delete(Long reviewNumber) { // 작성순번을 통해 삭제
        reviewRepository.deleteById(reviewNumber);
        if (reviewRepository.existsByReviewNumber(reviewNumber)) {
            return "success";
        } else {
            return "Error: Review not found";
        }
    }
}