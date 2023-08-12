package com.example.sales_post.Service;

import com.example.sales_post.DAO.ReviewDaoImpl;
import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Entity.ReviewEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.SalesPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<JSONObject> readAllByWriter(JSONObject jsonObject) {
        Map<String, Object> reviewMap = reviewDaoImpl.readAllByWriter((String) jsonObject.get("reviewWriter"));

        List<ReviewEntity> reviewEntityList = (List<ReviewEntity>) reviewMap.get("data");
        String result = (String) reviewMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            for (ReviewEntity entity : reviewEntityList) {
                JSONObject resultJsonObject = entityToJson(entity);
                jsonObjectList.add(resultJsonObject);
            }
            jsonObjectList.add(resultJsonObject(result));
        } else{
            jsonObjectList.add(resultJsonObject(result));
        }
        return jsonObjectList;
    }

    @Override
    public List<JSONObject> readAll() {
        Map<String, Object> reviewMap = reviewDaoImpl.readAll();

        List<ReviewEntity> reviewEntityList = (List<ReviewEntity>) reviewMap.get("data");
        String result = (String) reviewMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            for (ReviewEntity entity : reviewEntityList) {
                JSONObject resultJsonObject = entityToJson(entity);
                jsonObjectList.add(resultJsonObject);
            }
            jsonObjectList.add(resultJsonObject(result));
        } else{
            jsonObjectList.add(resultJsonObject(result));
        }
        return jsonObjectList;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        Map<String, Object> reviewMap = jsonToEntity(jsonObject);
        ReviewEntity reviewEntity = (ReviewEntity) reviewMap.get("data");
        String JTEresult = (String) reviewMap.get("result");
        String result;
        if(JTEresult.equals("success")){
            result = reviewDaoImpl.update(reviewEntity);
        } else {
            result = JTEresult;
        }
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

    @Override
    public JSONObject entityToJson(ReviewEntity reviewEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(reviewEntity, Map.class));
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }
}
