package dev.Store.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Store.DAO.SalesPostDAO;
import dev.Store.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
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
        Map<String, Object> resultMap = salesPostDAO.create(salesPostEntity);
        String result = (String) resultMap.get("result");
        JSONObject resultJsonObject;

        if (result.equals("success")) {
            resultJsonObject = resultJsonObject(result);
            Long salesPostNumber = (Long) resultMap.get("salesPostNumber");
            resultJsonObject.put("salesPostNumber", salesPostNumber);
        } else {
            resultJsonObject = resultJsonObject(result);
        }

        return resultJsonObject;
    }


    @Override
    public JSONObject read(JSONObject jsonObject) {
        Long salesPostNumber = Long.parseLong((String) jsonObject.get("salesPostNumber"));
        Map<String, Object> salesPostMap = salesPostDAO.readByWriterAndTitle(salesPostNumber);

        String result = (String) salesPostMap.get("result");
        JSONObject resultJsonObject;

        if (result.equals("success")) {
            SalesPostEntity salesPostEntity = (SalesPostEntity) salesPostMap.get("data");

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

        String result = (String) saelsPostMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            List<SalesPostEntity> salesPostEntityList = (List<SalesPostEntity>) saelsPostMap.get("data");

            for (SalesPostEntity entity : salesPostEntityList) {
                JSONObject temporaryJsonObject = entityToJson(entity);
                jsonObjectList.add(temporaryJsonObject);
            }
            return resultJsonObject(result, jsonObjectList);
        } else {
            return resultJsonObject(result);
        }
    }

    public JSONObject readAllByCategory(JSONObject jsonObject) {
        String category = (String) jsonObject.get("category");
        Map<String, Object> salesPostMap = salesPostDAO.readAllByCategory(category);

        String result = (String) salesPostMap.get("result");
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (result.equals("success")) {
            List<SalesPostEntity> salesPostEntityList = (List<SalesPostEntity>) salesPostMap.get("data");

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
    public JSONObject postLikeUpdate(JSONObject jsonObject){
        Long salesPostNumber = Long.parseLong((String) jsonObject.get("salesPostNumber"));
        String action = (String) jsonObject.get("disLike");

        Map<String, Object> resultMap = salesPostDAO.postLikeUpdate(salesPostNumber, action);
        String result = (String) resultMap.get("result");

        JSONObject resultJsonObject = resultJsonObject(result);
        if (result.equals("success")) {
            Long resultPostLike = (Long) resultMap.get("data");
            resultJsonObject.put("postLike", resultPostLike);
        }

        return resultJsonObject;
    }

    @Override
    public JSONObject postHitCountUpdate(JSONObject jsonObject){
        Long salesPostNumber = Long.parseLong((String) jsonObject.get("salesPostNumber"));

        Map<String, Object> resultMap = salesPostDAO.postHitCountUpdate(salesPostNumber);
        String result = (String) resultMap.get("result");

        JSONObject resultJsonObject = resultJsonObject(result);
        if (result.equals("success")) {
            Long resultPostHitCount = (Long) resultMap.get("data");
            resultJsonObject.put("postLike", resultPostHitCount);
        }

        return resultJsonObject;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        Long salesPostNumber = Long.parseLong((String) jsonObject.get("salesPostNumber"));
        String result = salesPostDAO.delete(salesPostNumber);
        return resultJsonObject(result);
    }

    @Override
    public SalesPostEntity jsonToEntity(JSONObject jsonObject) {
        return objectMapper.convertValue(jsonObject, SalesPostEntity.class);
    }

    @Override
    public JSONObject entityToJson(SalesPostEntity salesPostEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(salesPostEntity, JSONObject.class));
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
