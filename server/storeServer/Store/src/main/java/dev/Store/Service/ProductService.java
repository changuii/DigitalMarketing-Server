package dev.Store.Service;

import dev.Store.Entity.ProductEntity;
import dev.Store.Entity.SalesPostEntity;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface ProductService {
    public String create(ProductEntity product);
//    public Map<String, Object> readAllByPostNumber();
//    public Map<String, Object> readByNumber(Long number);
//    public String update(ProductEntity product);
//    public String delete(Long number);
    public ProductEntity jsonToEntity(JSONObject jsonObject);
    public String jsonListToEntityList(List<HashMap<String, Object>> jsonObjectList, SalesPostEntity salesPostEntity);
}
