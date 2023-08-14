package dev.Store.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Store.DAO.ProductDAO;
import dev.Store.DAO.SalesPostDAO;
import dev.Store.Entity.ProductEntity;
import dev.Store.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesPostServiceImpl implements SalesPostService{
    private final SalesPostDAO salesPostDAO;
    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(SalesPostServiceImpl.class);

    public SalesPostServiceImpl(@Autowired SalesPostDAO salesPostDAO,
                                @Autowired ProductService productService,
                                @Autowired ObjectMapper objectMapper) {
        this.salesPostDAO = salesPostDAO;
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @Override
    public JSONObject create(JSONObject jsonObject) {
        SalesPostEntity salesPostEntity = jsonToEntity(jsonObject);
        Map<String, Object> resultMap = salesPostDAO.create(salesPostEntity);
        String postCreateResult = (String) resultMap.get("result");
        SalesPostEntity resultSalesPostEntity = (SalesPostEntity) resultMap.get("data");

        if(postCreateResult.equals("success")) {
            List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) jsonObject.get("products");
            String productCreateResult = productService.jsonListToEntityList(products, resultSalesPostEntity);
            if(!postCreateResult.equals("succes"))
                return resultJsonObject(productCreateResult);
        }
        return resultJsonObject(postCreateResult);
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
        SalesPostEntity salesPostEntity = objectMapper.convertValue(jsonObject, SalesPostEntity.class);
        return salesPostEntity;
    }

    @Override
    public JSONObject entityToJson(SalesPostEntity salesPostEntity) {
        JSONObject jsonObject = new JSONObject(objectMapper.convertValue(salesPostEntity, Map.class));
//        JSONObject jsonObject = new JSONObject();
//
//        jsonObject.put("salesPostNumber", salesPostEntity.getPostNumber());
//        jsonObject.put("category", salesPostEntity.getCategory());
//        jsonObject.put("postTitle", salesPostEntity.getPostTitle());
//        jsonObject.put("postWriter", salesPostEntity.getPostWriter());
//        jsonObject.put("postContents", salesPostEntity.getPostContents());
//        jsonObject.put("postDate", salesPostEntity.getPostDate());
//        jsonObject.put("mainImage", salesPostEntity.getMainImage());
//        jsonObject.put("descImages", salesPostEntity.getDescImages());
//        jsonObject.put("postHitCount", salesPostEntity.getPostHitCount());
//        jsonObject.put("postLike", salesPostEntity.getPostLike());
//        jsonObject.put("storeLocation", salesPostEntity.getStoreLocation());

        List<ProductEntity> products = salesPostEntity.getProducts();
        List<JSONObject> jsonObjectList = new ArrayList<>();

        for (ProductEntity product : products) {
            JSONObject productJsonObject = new JSONObject(objectMapper.convertValue(product, Map.class));
            productJsonObject.put("number",product.getNumberById());
            jsonObjectList.add(productJsonObject);
        }
        jsonObject.put("products", jsonObjectList);

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
