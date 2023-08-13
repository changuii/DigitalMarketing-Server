package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ProductEntity;
import java.util.Map;

public interface ProductDao {
    String create(ProductEntity productEntity);
    Map<String, Object> read(Long productSerialNumber);
    Map<String, Object> readAll();
    String update(ProductEntity productEntity);
    String delete(Long productSerialNumber);
}