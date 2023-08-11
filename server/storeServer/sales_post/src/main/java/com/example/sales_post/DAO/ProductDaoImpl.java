package com.example.sales_post.DAO;

import com.example.sales_post.Entity.ProductEntity;
import com.example.sales_post.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;


//================================================================================
//                      전체적으로 NULL값에 대한 예외처리 필요
//================================================================================


@Repository
public class ProductDaoImpl implements ProductDao{
    private final ProductRepository productRepository;

    public ProductDaoImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity read(Long productSerialNumber) {
        return this.productRepository.findByProductSerialNumber(productSerialNumber);
    }

    @Override
    public List<ProductEntity> readAll() {
        return this.productRepository.findAll();
    }

    @Override
    public boolean create(ProductEntity productEntity) { // 자동 ID gen 때문에 중복 데이터가 들어가짐
        this.productRepository.save(productEntity);
        Long serialNumber = productEntity.getProductSerialNumber();
        return productRepository.existsByProductSerialNumber(serialNumber);
    }

    @Override
    public boolean update(ProductEntity productEntity) {
        // 수정하고자 하는 기존 데이터가 없는 경우: 새 데이터가 생성되도록 할 것인지, 아무 행동도 하지 않을 것인지
        productRepository.save(productEntity);
        return productRepository.existsByProductSerialNumber(productEntity.getProductSerialNumber());
    }

    @Override
    public boolean delete(Long productSerialNumber) { // NULL Pointer e 수정
        ProductEntity productEntity = productRepository.findByProductSerialNumber(productSerialNumber);
        productRepository.delete(productEntity);
        return productRepository.existsByProductSerialNumber(productEntity.getProductSerialNumber());
    }
}
