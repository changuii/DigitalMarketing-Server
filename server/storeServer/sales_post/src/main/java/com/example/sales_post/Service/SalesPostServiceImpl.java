package com.example.sales_post.Service;

import com.example.sales_post.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesPostServiceImpl implements SalesPostService {
    @Override
    public JSONObject create(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject readRecentByWriter(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<JSONObject> readAllByWriter(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<JSONObject> readAll() {
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
    public SalesPostEntity jsonToEntity(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject resultJsonObject(boolean result) {
        return null;
    }

    @Override
    public JSONObject resultJsonObject(boolean result, SalesPostEntity salesPostEntity) { return null;}

}