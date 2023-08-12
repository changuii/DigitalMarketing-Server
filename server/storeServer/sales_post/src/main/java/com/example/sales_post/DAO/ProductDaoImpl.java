package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Repository.ProductRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.List;
import java.util.Optional;



import javax.validation.*;


//================================================================================
//                      전체적으로 NULL값에 대한 예외처리 필요
//================================================================================


@Repository
public class ProductDaoImpl implements ProductDao{

    private final ProductRepository productRepository;

    public ProductDaoImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


//================================================================================
//  create 예외사항
//    1.빈 값이 들어오는 경우
//    2.일부 값만 들어오는 경우
//    3.이미 존재하는 값
//================================================================================

    @Autowired
    private Validator validator;

    public String validCheck(ProductEntity productEntity)
    {
        Set<ConstraintViolation<ProductEntity>> violations = validator.validate(productEntity);

        if (violations.isEmpty()) {
            return "success";
        } else {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<ProductEntity> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
            }
//            return new ValidationException(String.valueOf(errorMessage)).toString();
            return errorMessage.toString();
        }
    }


    @Override
    public String create(ProductEntity productEntity) {
        Long serialNumber = productEntity.getProductSerialNumber();
        String valid = validCheck(productEntity);

        if (!productRepository.existsByProductSerialNumber(serialNumber) && valid.equals("success")) {
            this.productRepository.save(productEntity);
        }
        return valid;
    }



    @Override
    public ProductEntity read(Long productSerialNumber) {
        return this.productRepository.findByProductSerialNumber(productSerialNumber);
    }

    @Override
    public List<ProductEntity> readAll() {
        return productRepository.findAll();
    }



//================================================================================
//  update 예외사항
//    1.존재하지 않는 값에대한 업데이트 요청
//================================================================================
    @Override
    public boolean update(ProductEntity productEntity) {
        try{
            if(productRepository.existsByProductSerialNumber(productEntity.getProductSerialNumber()))
            {
                ProductEntity oldProductEntity = productRepository.findByProductSerialNumber(productEntity.getProductSerialNumber());
                productEntity.setProductName(Optional.ofNullable(productEntity.getProductName()).orElse(oldProductEntity.getProductName()));
                productEntity.setProductPrice(Optional.ofNullable(productEntity.getProductPrice()).orElse(oldProductEntity.getProductPrice()));
                productEntity.setProductAmount(Optional.ofNullable(productEntity.getProductAmount()).orElse(oldProductEntity.getProductAmount()));
                productEntity.setProductDeliveryFee(Optional.ofNullable(productEntity.getProductDeliveryFee()).orElse(oldProductEntity.getProductDeliveryFee()));
                productEntity.setStoreLocation(Optional.ofNullable(productEntity.getStoreLocation()).orElse(oldProductEntity.getStoreLocation()));
                productRepository.save(productEntity);
                return true;
            }
        }catch (NullPointerException e){ return false; }
        return false;
    }

    @Override
    public boolean delete(Long productSerialNumber) {
        try{
            ProductEntity productEntity = productRepository.findByProductSerialNumber(productSerialNumber);
            productRepository.delete(productEntity);
            return true;
        }catch (NullPointerException e){ return false; }
    }
}
