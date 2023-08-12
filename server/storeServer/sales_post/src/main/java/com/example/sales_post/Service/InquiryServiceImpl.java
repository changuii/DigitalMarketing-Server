package com.example.sales_post.Service;

import com.example.sales_post.DAO.InquiryDaoImpl;
import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InquiryServiceImpl implements InquriyService {
    private final InquiryDaoImpl inquiryDaoImpl;
    private final ObjectMapper objectMapper;

    public InquiryServiceImpl(@Autowired InquiryDaoImpl inquiryDaoImpl,
                              @Autowired ObjectMapper objectMapper) {
        this.inquiryDaoImpl = inquiryDaoImpl;
        this.objectMapper = objectMapper;
    }

    public JSONObject create(JSONObject jsonObject){
        InquiryEntity inquiryEntity = jsonToEntity(jsonObject);
        String result = inquiryDaoImpl.create(inquiryEntity);
        return resultJsonObject(result);
    }

    public JSONObject readRecentByWriter(JSONObject jsonObject){
        String postNumberStr = (String) jsonObject.get("salesPostNumber");
        Long postNumber = Long.valueOf(postNumberStr);
        InquiryEntity inquiryEntity = inquiryDaoImpl
                .readRecentByWriter(postNumber, (String) jsonObject.get("inquiryWriter"));

        JSONObject resultJsonObject;
        if (inquiryEntity != null) {
            resultJsonObject = resultJsonObject("success", inquiryEntity);
        } else{
            resultJsonObject = resultJsonObject("fail");
        }
        return resultJsonObject;
    }

    public List<JSONObject> readAllByWriter(JSONObject jsonObject) {
        String postNumberStr = (String) jsonObject.get("salesPostNumber");
        Long postNumber = Long.valueOf(postNumberStr);
        List<InquiryEntity> inquiryEntityList = inquiryDaoImpl
                .readAllByWriter(postNumber, (String) jsonObject.get("inquiryWriter"));
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (inquiryEntityList == null || inquiryEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject("success");
            jsonObjectList.add(resultJsonObject);
        } else{
            for (InquiryEntity entity : inquiryEntityList) {
                JSONObject resultJsonObject = resultJsonObject("fail", entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    @Override
    public List<JSONObject> readAll() {
        List<InquiryEntity> inquiryEntityList = inquiryDaoImpl.readAll();
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (inquiryEntityList == null || inquiryEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject("success");
            jsonObjectList.add(resultJsonObject);
        } else{
            for (InquiryEntity entity : inquiryEntityList) {
                JSONObject resultJsonObject = resultJsonObject("fail", entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    public JSONObject update(JSONObject jsonObject){
        InquiryEntity inquiryEntity = jsonToEntity(jsonObject);
        String result = inquiryDaoImpl.update(inquiryEntity);
        return resultJsonObject(result);
    }

    public JSONObject delete(JSONObject jsonObject){
        String inquiryNumberStr = (String) jsonObject.get("inquiryNumber");
        Long inquiryNumber = Long.valueOf(inquiryNumberStr);
        String result = inquiryDaoImpl.delete(inquiryNumber);
        return resultJsonObject(result);
    }

    public InquiryEntity jsonToEntity(JSONObject jsonObject){
        return objectMapper.convertValue(jsonObject, InquiryEntity.class);
    }

    public JSONObject resultJsonObject(String result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result, InquiryEntity inquiryEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(inquiryEntity, Map.class));
        jsonObject.put("result", result);
        return jsonObject;
    }
}