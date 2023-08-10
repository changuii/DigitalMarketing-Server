package com.example.sales_post.Service;

import com.example.sales_post.Entity.ProductEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public JSONObject create(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject readOne(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<JSONObject> readAll(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        return null;
    }

    @Override
    public ProductEntity jsonToEntity(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject resultJsonObject(boolean result) {
        return null;
    }

    @Override
    public JSONObject resultJsonObject(boolean result, ProductEntity productEntity) {
        return null;
    }
}
