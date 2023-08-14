package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Repository.ProductRepository;
import com.example.sales_post.Service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.sales_post.Entity.SalesPostEntity;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao{

    private final ProductRepository productRepository;
    private final GlobalValidCheck globalValidCheck;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductDaoImpl(@Autowired ProductRepository productRepository,
                          @Autowired GlobalValidCheck globalValidCheck) {
        this.productRepository = productRepository;
        this.globalValidCheck = globalValidCheck;
    }

    @Transactional()
    @Override
    public String create(ProductEntity productEntity) {
        Long serialNumber = productEntity.getProductSerialNumber();
        String valid = globalValidCheck.validCheck(productEntity);

        if (valid.equals("success")) {
            this.productRepository.save(productEntity);

            SalesPostEntity salesPostEntity = productEntity.getSalesPostEntity();

            if (salesPostEntity != null) {
                salesPostEntity.addProduct(productEntity);
            }
        }
        return valid;
    }

    @Transactional()
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

    @Transactional()
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

    @Transactional()
    @Override
    public String update(ProductEntity productEntity) {
        Long serialNumber = productEntity.getProductSerialNumber();

        if(productRepository.existsByProductSerialNumber(serialNumber)) {
            ProductEntity oldProductEntity = productRepository.findByProductSerialNumber(productEntity.getProductSerialNumber());
            productEntity.setProductSerialNumber(Optional.ofNullable(productEntity.getProductSerialNumber()).orElse(oldProductEntity.getProductSerialNumber()));
            productEntity.setProductName(Optional.ofNullable(productEntity.getProductName()).orElse(oldProductEntity.getProductName()));
            productEntity.setProductPrice(Optional.ofNullable(productEntity.getProductPrice()).orElse(oldProductEntity.getProductPrice()));
            productEntity.setProductAmount(Optional.ofNullable(productEntity.getProductAmount()).orElse(oldProductEntity.getProductAmount()));
            productEntity.setProductDeliveryFee(Optional.ofNullable(productEntity.getProductDeliveryFee()).orElse(oldProductEntity.getProductDeliveryFee()));
            productEntity.setStoreLocation(Optional.ofNullable(productEntity.getStoreLocation()).orElse(oldProductEntity.getStoreLocation()));
            productEntity.setSalesPostEntity(Optional.ofNullable(productEntity.getSalesPostEntity()).orElse(oldProductEntity.getSalesPostEntity()));

            // 연관된 SalesPostEntity와의 연관 관계 설정
            SalesPostEntity salesPostEntity = productEntity.getSalesPostEntity();
            if (salesPostEntity != null) {
                salesPostEntity.addProduct(productEntity);
            }

            productRepository.save(productEntity);
            return "success";
        } else{
            return "Error: Product not found";
        }
    }

    @Transactional()
    @Override
    public String delete(Long productSerialNumber) {
        if (productRepository.existsByProductSerialNumber(productSerialNumber)) {
            ProductEntity productEntity = productRepository.findByProductSerialNumber(productSerialNumber);

            // 연관된 SalesPostEntity의 inquiries 컬렉션에서 삭제
            SalesPostEntity salesPostEntity = productEntity.getSalesPostEntity();
            if (salesPostEntity != null) {
                salesPostEntity.removeProduct(productEntity);
            }

            productRepository.deleteByProductSerialNumber(productSerialNumber);
            return "success";
        } else {
            return "Error: Inquiry not found";
        }
    }
}