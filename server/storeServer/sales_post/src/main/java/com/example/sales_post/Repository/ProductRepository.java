package com.example.sales_post.Repository;

import com.example.sales_post.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByproductPriceGreaterThan(int productPrice);

}
