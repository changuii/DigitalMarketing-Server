package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao{

    private final ProductRepository productRepository;

    public ProductDaoImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String create(ProductEntity productEntity) {
        productRepository.save(productEntity);

        if (productRepository.existsByProductSerialNumber(productEntity.getProductSerialNumber())) {
            return "success";
        } else {
            return "Error: Failed to create product";
        }
    }

    @Override
    public Map<String, Object> read(Long productSerialNumber) {
        ProductEntity productEntity = productRepository.findByProductSerialNumber(productSerialNumber);
        Map<String, Object> result = new HashMap<>();
        if (productEntity == null) {
            result.put("result", "Error: Product not found");
        } else {
            result.put("data", productEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public Map<String, Object> readAll() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        if (productEntityList.isEmpty()) {
            result.put("result", "Error: No products found");
        } else {
            result.put("data", productEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public String update(ProductEntity productEntity) {
        if(productRepository.existsByProductSerialNumber(productEntity.getProductSerialNumber())) {
            ProductEntity oldProductEntity = productRepository.findByProductSerialNumber(productEntity.getProductSerialNumber());
            productEntity.setProductSerialNumber(Optional.ofNullable(productEntity.getProductSerialNumber()).orElse(oldProductEntity.getProductSerialNumber()));
            productEntity.setProductName(Optional.ofNullable(productEntity.getProductName()).orElse(oldProductEntity.getProductName()));
            productEntity.setProductPrice(productEntity.getProductPrice() == 0 ? oldProductEntity.getProductPrice() : productEntity.getProductPrice());
            productEntity.setProductAmount(productEntity.getProductAmount() == 0 ? oldProductEntity.getProductAmount() : productEntity.getProductAmount());
            productEntity.setProductDeliveryFee(productEntity.getProductDeliveryFee() == 0 ? oldProductEntity.getProductDeliveryFee() : productEntity.getProductDeliveryFee());
            productEntity.setStoreLocation(Optional.ofNullable(productEntity.getStoreLocation()).orElse(oldProductEntity.getStoreLocation()));
            productRepository.save(productEntity);
            return "success";
        } else{
            return "Error: Product not found";
        }
    }

    @Override
    public String delete(Long productSerialNumber) {
        if (productRepository.existsByProductSerialNumber(productSerialNumber)) {
            productRepository.deleteByProductSerialNumber(productSerialNumber);
            return "success";
        } else {
            return "Error: Product not found";
        }
    }
}