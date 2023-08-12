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


//================================================================================
//  create 예외사항
//    1.빈 값이 들어오는 경우
//    2.일부 값만 들어오는 경우
//    3.이미 존재하는 값
//================================================================================
    @Override
    public boolean create(ProductEntity productEntity) {

        Long serialNumber = productEntity.getProductSerialNumber();
        if (productRepository.existsByProductSerialNumber(serialNumber)){
            return false;
        }
        else {
            this.productRepository.save(productEntity);
            return true;
        }
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
//
//================================================================================
    @Override
    public boolean update(ProductEntity productEntity) {
        Long oldProduct = productEntity.getProductSerialNumber();

        try{
            if(productRepository.existsByProductSerialNumber(oldProduct))
            {
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
