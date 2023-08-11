package com.example.sales_post.Service;

import com.example.sales_post.DAO.SalesPostDaoImpl;
import com.example.sales_post.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesPostServiceImpl implements SalesPostService{
    private final SalesPostDaoImpl salesPostDaoImpl;

    public SalesPostServiceImpl(@Autowired SalesPostDaoImpl salesPostDaoImpl) {
        this.salesPostDaoImpl = salesPostDaoImpl;
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

    @Override
    public SalesPostEntity jsonToEntity(JSONObject jsonObject) {
        String postNumberStr = (String) jsonObject.get("salesPostNumber");
        Long postNumber = Long.parseLong(postNumberStr);
        String postHitCountStr = (String) jsonObject.get("postHitCount");
        Long postHitCount = Long.parseLong(postHitCountStr);
        String postLikeStr = (String) jsonObject.get("postLike");
        Long postLike = Long.parseLong(postLikeStr);

        SalesPostEntity salesPostEntity = SalesPostEntity.builder()
                .postNumber(postNumber)
                .category((String) jsonObject.get("category"))
                .postTitle((String) jsonObject.get("postTitle"))
                .postWriter((String) jsonObject.get("postWriter"))
                .postDate((String) jsonObject.get("postDate"))
                .postContents((String) jsonObject.get("postContents"))
                .postPicture((String) jsonObject.get("postPicture"))
                .postHitCount(postHitCount)
                .postLike(postLike)
                .storeLocation((String) jsonObject.get("storeLocation"))
                .build();
        return salesPostEntity;
    }

    @Override
    public JSONObject resultJsonObject(boolean result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(boolean result, SalesPostEntity salesPostEntity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("salesPostNumber", salesPostEntity.getPostNumber());
        jsonObject.put("category", salesPostEntity.getCategory());
        jsonObject.put("postTitle", salesPostEntity.getPostTitle());
        jsonObject.put("postWriter", salesPostEntity.getPostWriter());
        jsonObject.put("postDate", salesPostEntity.getPostDate());
        jsonObject.put("postContents", salesPostEntity.getPostContents());
        jsonObject.put("postPicture", salesPostEntity.getPostPicture());
        jsonObject.put("poetHitCount", salesPostEntity.getPostHitCount());
        jsonObject.put("postLike", salesPostEntity.getPostLike());
        jsonObject.put("storeLocation", salesPostEntity.getStoreLocation());
        jsonObject.put("result", result);
        return jsonObject;
    }
}