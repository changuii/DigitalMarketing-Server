package com.example.sales_post.Service;

import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.SalesPostEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SalesPostServiceImpl implements SalesPostService{
    private final SalesPostDaoImpl salesPostDaoImpl;
    private final ObjectMapper objectMapper;

    public SalesPostServiceImpl(@Autowired SalesPostDaoImpl salesPostDaoImpl,
                                @Autowired ObjectMapper objectMapper) {
        this.salesPostDaoImpl = salesPostDaoImpl;
        this.objectMapper = objectMapper;
    }

    @Override
    public JSONObject create(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = jsonToEntity(jsonObject);
        String result = salesPostDaoImpl.create(salesPostEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject readRecentByWriter(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = salesPostDaoImpl
                .readRecentByWriter((String) jsonObject.get("postWriter"));
        JSONObject resultJsonObject;
        if (salesPostEntity != null) {
            resultJsonObject = resultJsonObject("success", salesPostEntity);
        } else{
            resultJsonObject = resultJsonObject("fail");
        }
        return resultJsonObject;
    }

    @Override
    public List<JSONObject> readAllByWriter(JSONObject jsonObject) {
        List<SalesPostEntity> salesPostEntityList = salesPostDaoImpl
                .readAllByWriter((String) jsonObject.get("postWriter"));
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (salesPostEntityList == null || salesPostEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject("success");
            jsonObjectList.add(resultJsonObject);
        } else{
            for (SalesPostEntity entity : salesPostEntityList) {
                JSONObject resultJsonObject = resultJsonObject("fail", entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    @Override
    public List<JSONObject> readAll() {
        List<SalesPostEntity> salesPostEntityList = salesPostDaoImpl.readAll();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        if (salesPostEntityList == null || salesPostEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject("success");
            jsonObjectList.add(resultJsonObject);
        } else {
            for (SalesPostEntity entity : salesPostEntityList) {
                JSONObject resultJsonObject = resultJsonObject("fail", entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = jsonToEntity(jsonObject);
        String result = salesPostDaoImpl.update(salesPostEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long postNumber = Long.valueOf((String) jsonObject.get("salesPostNumber"));
        String result = salesPostDaoImpl.delete(postNumber);
        return resultJsonObject(result);
    }

    public SalesPostEntity jsonToEntity(JSONObject jsonObject){
        return objectMapper.convertValue(jsonObject, SalesPostEntity.class);
    }

    @Override
    public JSONObject resultJsonObject(String result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result, SalesPostEntity salesPostEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(salesPostEntity, Map.class));
        jsonObject.put("result", result);
        return jsonObject;
    }

    public JSONObject resultJsonObjectList(String result, List<JSONObject> jsonObjectList){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonObjectList);
        jsonObject.put("result", result);
        return jsonObject;
    }
}

