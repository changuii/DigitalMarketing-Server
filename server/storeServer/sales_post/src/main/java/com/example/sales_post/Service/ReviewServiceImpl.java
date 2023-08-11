package com.example.sales_post.Service;

import com.example.sales_post.DAO.ReviewDaoImpl;
import com.example.sales_post.Entity.ReviewEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.SalesPostRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
   private final ReviewDaoImpl reviewDaoImpl;
    private final SalesPostRepository salesPostRepository;

    public ReviewServiceImpl (@Autowired ReviewDaoImpl reviewDaoImpl,
                              @Autowired SalesPostRepository salesPostRepository){
        this.reviewDaoImpl = reviewDaoImpl;
        this.salesPostRepository = salesPostRepository;
    }
    @Override
    public JSONObject create(JSONObject jsonObject) {
        ReviewEntity reviewEntity = jsonToEntity(jsonObject);
        boolean result = reviewDaoImpl.create(reviewEntity);
        return resultJsonObject(result);
    }
    @Override
    public List<JSONObject> readAllByWriter(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<JSONObject> readAll() {
        List<ReviewEntity> reviewEntityList = reviewDaoImpl.readAll();
        return null;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        ReviewEntity reviewEntity = jsonToEntity(jsonObject);
        boolean result = reviewDaoImpl.update(reviewEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long reviewNumber = Long.valueOf((String) jsonObject.get("reviewNumber"));
        boolean result = reviewDaoImpl.delete(reviewNumber);
        return resultJsonObject(result);
    }

    @Override
    public ReviewEntity jsonToEntity(JSONObject jsonObject) {
        Long postNumber = Long.parseLong((String) jsonObject.get("salesPostNumber"));
        SalesPostEntity salesPostEntity = salesPostRepository.findByPostNumber(postNumber);

        Long reviewNumber = Long.valueOf((String) jsonObject.get("reviewNumber"));

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .reviewNumber(reviewNumber)
                .reviewAuthor((String) jsonObject.get("reviewAuthor"))
                .reviewStarRating(Integer.parseInt(jsonObject.get("reviewStarRating").toString()))
                .reviewContents((String) jsonObject.get("reviewContents"))
                .reviewLike(Integer.parseInt(jsonObject.get("reviewLike").toString()))
                .reviewDate((String) jsonObject.get("reviewDate"))
                .salesPostEntity(salesPostEntity)
                .build();
        return reviewEntity;
    }

    @Override
    public JSONObject resultJsonObject(boolean result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(boolean result, ReviewEntity reviewEntity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reviewNumber",reviewEntity.getReviewNumber());
        jsonObject.put("reviewAuthor",reviewEntity.getReviewNumber());
        jsonObject.put("reviewStarRating",reviewEntity.getReviewNumber());
        jsonObject.put("reviewContents",reviewEntity.getReviewNumber());
        jsonObject.put("reviewLike",reviewEntity.getReviewNumber());
        jsonObject.put("reviewDate",reviewEntity.getReviewNumber());
        jsonObject.put("salesPostNumber", reviewEntity.getSalesPostEntity().getPostNumber());
        jsonObject.put(("result"),result);
        return jsonObject;
    }
}
