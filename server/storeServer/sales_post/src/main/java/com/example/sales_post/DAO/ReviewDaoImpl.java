package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ReviewEntity;
import com.example.sales_post.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImpl implements ReviewDao{
    private final ReviewRepository reviewRepository;

    public ReviewDaoImpl(@Autowired ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
    @Override
    public boolean create(ReviewEntity reviewEntity) {
        if(reviewRepository.existsByReviewAuthor(reviewEntity.getReviewAuthor())) // 중복작성 방지, 제품을 두 번 이상 구매하는 경우도 있는데 일단 넣었음.
            return false;
        else{
            reviewRepository.save(reviewEntity);
        return true;
        }
    }
    @Override
    public List<ReviewEntity> readAllByWriter(String reviewAuthor) { //작성자의 모든 리뷰 조회. 작성날짜에 따른 정렬기능 필요
        List<ReviewEntity> reviewEntityList = reviewRepository.readAllByReviewAuthor(reviewAuthor);
        return reviewEntityList;
    }
    @Override
    public List<ReviewEntity> readAll() { //모든 리뷰 조회
        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
        return reviewEntityList;
    }
    @Override
    public boolean update(ReviewEntity reviewEntity) {
        if(reviewRepository.existsByReviewAuthor(reviewEntity.getReviewAuthor()) && reviewRepository.existsByReviewNumber(reviewEntity.getReviewNumber())){
            reviewRepository.save(reviewEntity); // 수정할 엔티티티의 작성자와 작성번호가 존재할 시 수정
            return true;
        }
        else {
        return false;}
    }
    @Override
    public boolean delete(Long reviewNumber) { // 작성순번을 통해 삭제
        reviewRepository.deleteById(reviewNumber);
        return !reviewRepository.existsByReviewNumber(reviewNumber); //삭제해도 존재하면 false, 없을 시 true

        }
    }

