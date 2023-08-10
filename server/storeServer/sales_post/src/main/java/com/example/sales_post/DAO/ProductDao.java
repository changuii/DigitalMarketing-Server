package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ProductEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface ProductDao {
    boolean create(ProductEntity productEntity);
    ProductEntity readOne(Long productSerialNumber);
    List<ProductEntity> readAll(Long productSerialNumber);
    boolean update(ProductEntity productEntity);
    boolean delete(Long productSerialNumber);
}
