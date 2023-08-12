package com.example.sales_post.Service;

import com.example.sales_post.DAO.ProductDaoImpl;
import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService{
    private final ProductDaoImpl productDaoImpl;
    private final SalesPostDaoImpl salesPostDaoImpl;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(@Autowired ProductDaoImpl productDaoImpl,
                              @Autowired ObjectMapper objectMapper,
                              @Autowired SalesPostDaoImpl salesPostDaoImpl) {
        this.productDaoImpl = productDaoImpl;
        this.salesPostDaoImpl = salesPostDaoImpl;
        this.objectMapper = objectMapper;
    }

    @Override
    public JSONObject create(JSONObject jsonObject) {
        Map<String, Object> productMap = jsonToEntity(jsonObject);
        ProductEntity productEntity = (ProductEntity) productMap.get("data");
        String JTEresult = (String) productMap.get("result");
        String result;
        if(JTEresult.equals("success")){
            result = productDaoImpl.create(productEntity);
        } else {
            result = JTEresult;
        }
        return resultJsonObject(result);
    }

    @Override
    public JSONObject read(JSONObject jsonObject) {
        Long serialNumber = Long.valueOf((String) jsonObject.get("productSerialNumber"));
        Map<String, Object> productMap = productDaoImpl.read(serialNumber);

        ProductEntity productEntity = (ProductEntity) productMap.get("data");
        String result = (String) productMap.get("result");
        JSONObject resultJsonObject;

        if (result.equals("success")) {
            resultJsonObject = entityToJson(productEntity);
        } else{
            resultJsonObject = resultJsonObject(result);
        }
        return resultJsonObject;
    }

    @Override
    public List<JSONObject> readAll() {
        Map<String, Object> productMap = productDaoImpl.readAll();

        List<ProductEntity> productEntityList = (List<ProductEntity>) productMap.get("data");
        String result = (String) productMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            for (ProductEntity entity : productEntityList) {
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
        Map<String, Object> productMap = jsonToEntity(jsonObject);
        ProductEntity productEntity = (ProductEntity) productMap.get("data");
        String JTEresult = (String) productMap.get("result");
        String result;
        if(JTEresult.equals("success")){
            result = productDaoImpl.update(productEntity);
        } else {
            result = JTEresult;
        }
        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long serialNumber = Long.valueOf((String) jsonObject.get("productSerialNumber"));
        String result = productDaoImpl.delete(serialNumber);
        return resultJsonObject(result);
    }

    @Override
    public Map<String, Object> jsonToEntity(JSONObject jsonObject){
        ProductEntity productEntity = objectMapper.convertValue(jsonObject, ProductEntity.class);
        Long salesPostNumber = Long.valueOf((String) jsonObject.get("salesPostNumber"));

        Map<String, Object> salesPostMap = salesPostDaoImpl.read(salesPostNumber);
        String result = (String) salesPostMap.get("result");

        Map<String, Object> productMap = new HashMap<>();
        if(result.equals("success")){
            productEntity.setSalesPostEntity((SalesPostEntity) salesPostMap.get("data"));
            productMap.put("data", productEntity);
            productMap.put("result", result);
        } else{
            productMap.put("result", result);
        }

        return productMap;
    }

    @Override
    public JSONObject entityToJson(ProductEntity productEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(productEntity, Map.class));
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }
}