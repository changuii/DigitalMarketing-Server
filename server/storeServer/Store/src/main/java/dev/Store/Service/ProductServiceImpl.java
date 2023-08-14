package dev.Store.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.Store.DAO.ProductDAO;
import dev.Store.Entity.ProductEntity;
import dev.Store.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductDAO productDAO;
    private final ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(SalesPostServiceImpl.class);

    public ProductServiceImpl(@Autowired ProductDAO productDAO,
                              @Autowired ObjectMapper objectMapper) {
        this.productDAO = productDAO;
        this.objectMapper = objectMapper;
    }

    public String create(ProductEntity product) {
        return productDAO.create(product);
    }

//    public Map<String, Object> readAllByPostNumber() {
//        return productDAO.readAll();
//    }
//
//    public Map<String, Object> readByNumber(Long number) {
//        return productDAO.readByNumber(number);
//    }
//
//    public String update(ProductEntity product) {
//        return productDAO.update(product);
//    }
//
//    public String delete(Long number) {
//        return productDAO.delete(number);
//    }

    public ProductEntity jsonToEntity(JSONObject jsonObject) {
        return objectMapper.convertValue(jsonObject, ProductEntity.class);
    }

    public ProductEntity jsonToEntity(Map<String, Object> map, Long postNumber) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(Long.parseLong((String) map.get("number")), postNumber);
        productEntity.setDetail((String) map.get("detail"));
        productEntity.setQuantity(Long.parseLong((String) map.get("quantity")));
        productEntity.setPrice(Long.parseLong((String) map.get("price")));
        return productEntity;
    }

    public String jsonListToEntityList(List<HashMap<String, Object>> jsonObjectList, SalesPostEntity salesPostEntity){

        for (HashMap<String, Object> jsonObject : jsonObjectList) {
            ProductEntity productEntity = jsonToEntity(jsonObject, salesPostEntity.getPostNumber());
            productEntity.setSalesPostEntity(salesPostEntity);
            String result = create(productEntity);

            if(!result.equals("success")) {
                return "Error";
            }
        }

        return "success";
    }
}
