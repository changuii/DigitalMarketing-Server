package com.example.sales_post.Service;

import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.SalesPostEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SalesPostServiceImpl implements SalesPostService{
    private static final Logger logger = LoggerFactory.getLogger(SalesPostServiceImpl.class);
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
        boolean result = salesPostDaoImpl.create(salesPostEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject readRecentByWriter(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = salesPostDaoImpl
                .readRecentByWriter((String) jsonObject.get("postWriter"));
        JSONObject resultJsonObject;
        if (salesPostEntity != null) {
            resultJsonObject = resultJsonObject(true, salesPostEntity);
        } else{
            resultJsonObject = resultJsonObject(false);
        }
        return resultJsonObject;
    }

    @Override
    public List<JSONObject> readAllByWriter(JSONObject jsonObject) {
        List<SalesPostEntity> salesPostEntityList = salesPostDaoImpl
                .readAllByWriter((String) jsonObject.get("postWriter"));
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (salesPostEntityList == null || salesPostEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject(false);
            jsonObjectList.add(resultJsonObject);
        } else{
            for (SalesPostEntity entity : salesPostEntityList) {
                JSONObject resultJsonObject = resultJsonObject(true, entity);
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
            JSONObject resultJsonObject = resultJsonObject(false);
            jsonObjectList.add(resultJsonObject);
        } else{
            for (SalesPostEntity entity : salesPostEntityList) {
                JSONObject resultJsonObject = resultJsonObject(true, entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = jsonToEntity(jsonObject);
        boolean result = salesPostDaoImpl.update(salesPostEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        String salesPostNumberStr = (String) jsonObject.get("salesPostNumber");
        Long postNumber = Long.valueOf(salesPostNumberStr);
        boolean result = salesPostDaoImpl.delete(postNumber);
        return resultJsonObject(result);
    }

    public SalesPostEntity jsonToEntity(JSONObject jsonObject){
        return objectMapper.convertValue(jsonObject, SalesPostEntity.class);
    }

    @Override
    public JSONObject resultJsonObject(boolean result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(boolean result, SalesPostEntity salesPostEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(salesPostEntity, Map.class));
        jsonObject.put("result", result);
        return jsonObject;
    }
}

