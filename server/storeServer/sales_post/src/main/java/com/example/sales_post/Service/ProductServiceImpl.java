package com.example.sales_post.Service;

import com.example.sales_post.DAO.ProductDaoImpl;
import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService{
    private final ProductDaoImpl productDaoimpl;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(@Autowired ProductDaoImpl productDaoimpl,
                              @Autowired ObjectMapper objectMapper) {
        this.productDaoimpl = productDaoimpl;
        this.objectMapper = objectMapper;
    }



    //================================================================================
    // TODO: 실패시 오류 내용 전송
    //================================================================================


    @Override
    public JSONObject create(JSONObject jsonObject) {
        ProductEntity productEntity = jsonToEntity(jsonObject);
        String result = "fail";
        if(productDaoimpl.create(productEntity)) {result = "success";}

        return resultJsonObject(result);
    }

    @Override
    public JSONObject read(JSONObject jsonObject) {
        Long serialNumber = Long.parseLong(jsonObject.get("productSerialNumber").toString());
        ProductEntity productEntity = productDaoimpl.read(serialNumber);

        if(productEntity == null) { return resultJsonObject("fail"); } // read 실패: 찾는 값이 없는 경우
        else { return resultJsonObject("success",productEntity); } // read 성공
    }

    @Override
    public List<JSONObject> readAll() {
        List<ProductEntity> productEntityList = productDaoimpl.readAll();
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (productEntityList == null || productEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject("fail"); // readAll 실패
            jsonObjectList.add(resultJsonObject);
        } else{
            for (ProductEntity entity : productEntityList) {
                JSONObject resultJsonObject = resultJsonObject("success", entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }


    @Override
    public JSONObject update(JSONObject jsonObject) {
        ProductEntity productEntity = jsonToEntity(jsonObject);
        String result = "fail";
        if (productDaoimpl.update(productEntity)){ result = "success";}

        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long serialNumber = Long.parseLong(jsonObject.get("productSerialNumber").toString());
        String result = "fail";
        if(productDaoimpl.delete(serialNumber)){result = "success";}
        return resultJsonObject(result);
    }

    @Override
    public ProductEntity jsonToEntity(JSONObject jsonObject){
        return objectMapper.convertValue(jsonObject, ProductEntity.class);
    }

    @Override
    public JSONObject resultJsonObject(String result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result, ProductEntity productEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(productEntity, Map.class));
        jsonObject.put("result", result);
        return jsonObject;
    }
}