package dev.Store.Service;

import dev.Store.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesPostService {
    public JSONObject create(JSONObject jsonObject);
    public JSONObject createComment(JSONObject jsonObject);
    public JSONObject read(JSONObject jsonObject);
    public JSONObject readAllByCategory(JSONObject jsonObject);
    public JSONObject readAll();
    public JSONObject update(JSONObject jsonObject);
    public JSONObject postHitCountUpdate(JSONObject jsonObject);
    public JSONObject postLikeUpdate(JSONObject jsonObject);
    public JSONObject delete(JSONObject jsonObject);
    public JSONObject deleteComment(JSONObject jsonObject);
    SalesPostEntity jsonToEntity(JSONObject jsonObject);
    JSONObject entityToJson(SalesPostEntity salesPostEntity);
    JSONObject resultJsonObject(String result);
    JSONObject resultJsonObject(String result, List<JSONObject> jsonObjectList);
}
