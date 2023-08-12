package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ReviewEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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
            return "fail";
        }
    }

    @Override
    public List<ReviewEntity> readAllByWriter(String reviewWriter) { //작성자의 모든 리뷰 조회. 작성날짜에 따른 정렬기능 필요
        List<ReviewEntity> reviewEntityList = reviewRepository.readAllByReviewWriter(reviewWriter);
        return reviewEntityList;
    }

    @Override
    public List<ReviewEntity> readAll() { //모든 리뷰 조회
        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
        return reviewEntityList;
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
            return "fail";
        }
    }

    @Override
    public String delete(Long reviewNumber) { // 작성순번을 통해 삭제
        reviewRepository.deleteById(reviewNumber);
        if (reviewRepository.existsByReviewNumber(reviewNumber)) {
            return "success";
        } else {
            return "fail";
        }
    }
}