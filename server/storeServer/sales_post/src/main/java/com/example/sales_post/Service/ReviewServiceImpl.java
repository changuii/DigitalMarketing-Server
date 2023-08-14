package com.example.sales_post.Service;

import com.example.sales_post.DAO.ReviewDaoImpl;
import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.ReviewEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewDaoImpl reviewDaoImpl;
    private final SalesPostDaoImpl salesPostDaoImpl;
    private final ObjectMapper objectMapper;

    public ReviewServiceImpl(@Autowired ReviewDaoImpl reviewDaoImpl,
                             @Autowired SalesPostDaoImpl salesPostDaoImpl,
                             @Autowired ObjectMapper objectMapper) {
        this.reviewDaoImpl = reviewDaoImpl;
        this.salesPostDaoImpl = salesPostDaoImpl;
        this.objectMapper = objectMapper;
    }

    @Override
    public JSONObject create(JSONObject jsonObject) {
        Map<String, Object> reviewMap = jsonToEntity(jsonObject);
        ReviewEntity reviewEntity = (ReviewEntity) reviewMap.get("data");
        String JTEresult = (String) reviewMap.get("result");
        String result;

        if(JTEresult.equals("success")){
            result = reviewDaoImpl.create(reviewEntity);
        } else {
            result = JTEresult;
        }
        return resultJsonObject(result);
    }

    @Override
    public JSONObject readAllByWriter(JSONObject jsonObject) {
        Map<String, Object> reviewMap = reviewDaoImpl.readAllByWriter((String) jsonObject.get("reviewWriter"));

        List<ReviewEntity> reviewEntityList = (List<ReviewEntity>) reviewMap.get("data");
        String result = (String) reviewMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            for (ReviewEntity entity : reviewEntityList) {
                JSONObject temporaryJsonObject = entityToJson(entity);
                jsonObjectList.add(temporaryJsonObject);
            }
            return resultJsonObject(result, jsonObjectList);
        } else {
            return resultJsonObject(result);
        }
    }

    @Override
    public JSONObject readAll() {
        Map<String, Object> reviewMap = reviewDaoImpl.readAll();

        List<ReviewEntity> reviewEntityList = (List<ReviewEntity>) reviewMap.get("data");
        String result = (String) reviewMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            for (ReviewEntity entity : reviewEntityList) {
                JSONObject temporaryJsonObject = entityToJson(entity);
                jsonObjectList.add(temporaryJsonObject);
            }
            return resultJsonObject(result, jsonObjectList);
        } else {
            return resultJsonObject(result);
        }
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        ReviewEntity reviewEntity = updateJsonToEntity(jsonObject);
        String result = reviewDaoImpl.update(reviewEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long reviewNumber = Long.valueOf((String) jsonObject.get("reviewNumber"));
        String result = reviewDaoImpl.delete(reviewNumber);
        return resultJsonObject(result);
    }

    public Map<String, Object> jsonToEntity(JSONObject jsonObject){
        ReviewEntity reviewEntity = objectMapper.convertValue(jsonObject, ReviewEntity.class);
        Long salesPostNumber = Long.valueOf((String) jsonObject.get("salesPostNumber"));

        Map<String, Object> salesPostMap = salesPostDaoImpl.read(salesPostNumber);
        String result = (String) salesPostMap.get("result");

        Map<String, Object> reviewMap = new HashMap<>();
        if(result.equals("success")){
            reviewEntity.setSalesPostEntity((SalesPostEntity) salesPostMap.get("data"));
            reviewMap.put("data", reviewEntity);
            reviewMap.put("result", result);
        } else{
            reviewMap .put("result", result);
        }

        return reviewMap;
    }

    public ReviewEntity updateJsonToEntity(JSONObject jsonObject){
        return objectMapper.convertValue(jsonObject, ReviewEntity.class);
    }

    @Override
    public JSONObject entityToJson(ReviewEntity reviewEntity) {
//        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(reviewEntity, Map.class));
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("reviewNumber", reviewEntity.getReviewNumber());
        jsonObject.put("reviewWriter", reviewEntity.getReviewWriter());
        jsonObject.put("reviewContents", reviewEntity.getReviewContents());
        jsonObject.put("reviewStarRating", reviewEntity.getReviewStarRating());
        jsonObject.put("reviewLike", reviewEntity.getReviewLike());
        jsonObject.put("reviewDate", reviewEntity.getReviewDate());

        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result, List<JSONObject> jsonObjectList){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonObjectList);
        jsonObject.put("result", result);
        return jsonObject;
    }
}
