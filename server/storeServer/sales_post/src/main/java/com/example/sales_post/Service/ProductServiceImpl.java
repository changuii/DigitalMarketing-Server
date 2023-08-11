package com.example.sales_post.Service;

import com.example.sales_post.DAO.ProductDaoImpl;
import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.ProductRepository;
import com.sun.istack.Nullable;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService{
    private final ProductDaoImpl productDaoimpl;

    public ProductServiceImpl(@Autowired ProductDaoImpl productDaoimpl) {
        this.productDaoimpl = productDaoimpl;
    }


    @Override
    public JSONObject read(JSONObject jsonObject) {
        Long serialNumber = Long.parseLong(jsonObject.get("productSerialNumber").toString());
        ProductEntity productEntity = productDaoimpl.read(serialNumber);

        if(productEntity == null) { return resultJsonObject(false); }
        else { return resultJsonObject(true,productEntity); }
    }

    @Override
    public List<JSONObject> readAll() {
        List<ProductEntity> productEntityList = productDaoimpl.readAll();
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (productEntityList == null || productEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject(false);
            jsonObjectList.add(resultJsonObject);
        } else{
            for (ProductEntity entity : productEntityList) {
                JSONObject resultJsonObject = resultJsonObject(true, entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }


    @Override
    public JSONObject create(JSONObject jsonObject) {
        ProductEntity productEntity = jsonToEntity(jsonObject);
        boolean result = productDaoimpl.create(productEntity);
        return resultJsonObject(result,productEntity);
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        ProductEntity productEntity = jsonToEntity(jsonObject);
        return resultJsonObject(productDaoimpl.update(productEntity));
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long serialNumber = Long.parseLong(jsonObject.get("productSerialNumber").toString());
        return resultJsonObject(productDaoimpl.delete(serialNumber));
    }




    @Override
    public ProductEntity jsonToEntity(JSONObject jsonObject) {
        @Nullable
        Long oldserialNumber = Long.parseLong((jsonObject.get("productSerialNumber").toString())); //old product
        Long serialNumber = Long.parseLong(jsonObject.get("productSerialNumber").toString()); // new or modify target product
        ProductEntity productEntity = productDaoimpl.read(oldserialNumber);// get old

        productEntity = ProductEntity.builder()
                .productSerialNumber(serialNumber)
                .productName(jsonObject.get("productName").toString())
                .productPrice(Integer.parseInt(jsonObject.get("productPrice").toString()))
                .productAmount(Integer.parseInt(jsonObject.get("productAmount").toString()))
                .productDeliveryFee(Integer.parseInt(jsonObject.get("productDeliveryFee").toString()))
                .storeLocation(jsonObject.get("storeLocation").toString())
                .build();

            return productEntity;
    }


    public JSONObject resultJsonObject(boolean result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }


    public JSONObject resultJsonObject(boolean result, ProductEntity productEntity){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productSerialNumber ", productEntity.getProductSerialNumber());
        jsonObject.put("productName", productEntity.getProductName());
        jsonObject.put("productPrice", productEntity.getProductPrice());
        jsonObject.put("productAmount", productEntity.getProductAmount());
        jsonObject.put("productDeliveryFee", productEntity.getProductDeliveryFee());
        jsonObject.put("storeLocation", productEntity.getStoreLocation());
        jsonObject.put("result", result);
        return jsonObject;
    }

}