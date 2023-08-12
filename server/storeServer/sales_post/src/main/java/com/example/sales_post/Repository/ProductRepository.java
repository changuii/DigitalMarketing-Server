package com.example.sales_post.Repository;

import com.example.sales_post.Entity.ProductEntity;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    ProductEntity findByProductSerialNumber(Long productSerialNumber);
    boolean existsByProductSerialNumber(Long productSerialNumber);
}
