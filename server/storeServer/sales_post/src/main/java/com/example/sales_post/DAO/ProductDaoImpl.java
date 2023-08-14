package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Repository.ProductRepository;
import com.example.sales_post.Service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


//================================================================================
//                      전체적으로 NULL값에 대한 예외처리 필요
//================================================================================


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

//    public String validCheck(@Validated ProductEntity productEntity)
//    {
//        Set<ConstraintViolation<ProductEntity>> violations = validator.validate(productEntity);
//        logger.info(violations.toString());
//        logger.info(productEntity.toString());
//        if (violations.isEmpty()) {
//            return "success";
//        } else {
//            StringBuilder errorMessage = new StringBuilder();
//            for (ConstraintViolation<ProductEntity> violation : violations) {
//                errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage());
//            }
////            return new ValidationException(String.valueOf(errorMessage)).toString();
////            return new ValidationFailedException(String.valueOf(errorMessage)).toString();
//
//            return errorMessage.toString();
//        }
//    }


    @Override
    public String create(ProductEntity productEntity) {
        Long serialNumber = productEntity.getProductSerialNumber();
        String valid = globalValidCheck.validCheck(productEntity);

        logger.info(productEntity.toString());
        if (!productRepository.existsByProductSerialNumber(serialNumber) && valid.equals("success")) {
            this.productRepository.save(productEntity);
        }
        return valid;
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
        }
        return "ERROR: Product not found";
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