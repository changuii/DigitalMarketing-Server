package com.example.sales_post.Service;

import com.example.sales_post.DAO.ReviewDaoImpl;
import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.ReviewEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.SalesPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewDaoImpl reviewDaoImpl;
    private final ObjectMapper objectMapper;

    public ReviewServiceImpl (@Autowired ReviewDaoImpl reviewDaoImpl,
                              @Autowired ObjectMapper objectMapper){
        this.reviewDaoImpl = reviewDaoImpl;
        this.objectMapper = objectMapper;
    }
    @Override
    public JSONObject create(JSONObject jsonObject) {
        ReviewEntity reviewEntity = jsonToEntity(jsonObject);
        String result = reviewDaoImpl.create(reviewEntity);
        return resultJsonObject(result);
    }
    @Override
    public List<JSONObject> readAllByWriter(JSONObject jsonObject) {
        List<ReviewEntity> reviewEntityList = reviewDaoImpl.readAllByWriter((String) jsonObject.get("reviewWriter"));
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (reviewEntityList == null || reviewEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject("success");
            jsonObjectList.add(resultJsonObject);
        } else{
            for (ReviewEntity entity : reviewEntityList) {
                JSONObject resultJsonObject = resultJsonObject("fail", entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    @Override
    public List<JSONObject> readAll() {
        List<ReviewEntity> reviewEntityList = reviewDaoImpl.readAll();
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (reviewEntityList == null || reviewEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject("success");
            jsonObjectList.add(resultJsonObject);
        } else{
            for (ReviewEntity entity : reviewEntityList) {
                JSONObject resultJsonObject = resultJsonObject("fail", entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        ReviewEntity reviewEntity = jsonToEntity(jsonObject);
        String  result = reviewDaoImpl.update(reviewEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long reviewNumber = Long.valueOf((String) jsonObject.get("reviewNumber"));
        String result = reviewDaoImpl.delete(reviewNumber);
        return resultJsonObject(result);
    }

    public ReviewEntity jsonToEntity(JSONObject jsonObject){
        return objectMapper.convertValue(jsonObject, ReviewEntity.class);
    }

    @Override
    public JSONObject resultJsonObject(String result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result, ReviewEntity reviewEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(reviewEntity, Map.class));
        jsonObject.put("result", result);
        return jsonObject;
    }
}
