package dev.Store.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Store.DAO.SalesPostDAO;
import dev.Store.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SalesPostServiceImpl implements SalesPostService{
    private final SalesPostDAO salesPostDAO;
    private final ObjectMapper objectMapper;

    public SalesPostServiceImpl(@Autowired SalesPostDAO salesPostDAO,
                                @Autowired ObjectMapper objectMapper) {
        this.salesPostDAO = salesPostDAO;
        this.objectMapper = objectMapper;
    }

    @Override
    public JSONObject create(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = jsonToEntity(jsonObject);
        String result = salesPostDAO.create(salesPostEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject read(JSONObject jsonObject) {
        String postWriter = (String) jsonObject.get("postWriter");
        String postTitle = (String) jsonObject.get("postTitle");
        Map<String, Object> salesPostMap = salesPostDAO.readByWriterAndTitle(postWriter, postTitle);

        SalesPostEntity salesPostEntity = (SalesPostEntity) salesPostMap.get("data");
        String result = (String) salesPostMap.get("result");
        JSONObject resultJsonObject;

        if (result.equals("success")) {
            resultJsonObject = entityToJson(salesPostEntity);
            resultJsonObject.put("result", result);
        } else {
            resultJsonObject = resultJsonObject(result);
        }
        return resultJsonObject;
    }

    @Override
    public JSONObject readAll() {
        Map<String, Object> saelsPostMap = salesPostDAO.readAll();

        List<SalesPostEntity> salesPostEntityList = (List<SalesPostEntity>) saelsPostMap.get("data");
        String result = (String) saelsPostMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            for (SalesPostEntity entity : salesPostEntityList) {
                JSONObject temporaryJsonObject = entityToJson(entity);
                jsonObjectList.add(temporaryJsonObject);
            }
            return resultJsonObject(result, jsonObjectList);
        } else {
            return resultJsonObject(result);
        }
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = jsonToEntity(jsonObject);
        String result = salesPostDAO.update(salesPostEntity);
        return resultJsonObject(result);
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        String postWriter = (String) jsonObject.get("postWriter");
        String postTitle = (String) jsonObject.get("postTitle");
        String result = salesPostDAO.delete(postWriter, postTitle);
        return resultJsonObject(result);
    }

    @Override
    public SalesPostEntity jsonToEntity(JSONObject jsonObject) {
        return objectMapper.convertValue(jsonObject, SalesPostEntity.class);
    }

    @Override
    public JSONObject entityToJson(SalesPostEntity salesPostEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(salesPostEntity, Map.class));
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject resultJsonObject(String result, List<JSONObject> jsonObjectList){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonObjectList);
        jsonObject.put("result", result);
        return jsonObject;
    }
}
