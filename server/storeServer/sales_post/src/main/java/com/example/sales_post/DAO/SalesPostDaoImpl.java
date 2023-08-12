package com.example.sales_post.DAO;

import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.SalesPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class SalesPostDaoImpl implements SalesPostDao {
    private final SalesPostRepository salesPostRepository;

    public SalesPostDaoImpl(@Autowired SalesPostRepository salesPostRepository) {
        this.salesPostRepository = salesPostRepository;
    }

    @Override
    public boolean create(SalesPostEntity salesPostEntity) {
        salesPostRepository.save(salesPostEntity);
        if (salesPostRepository.existsByPostNumber(salesPostEntity.getPostNumber())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SalesPostEntity read(Long postNumber) {
        return salesPostRepository.findByPostNumber(postNumber);
    }

    @Override
    public SalesPostEntity readRecentByWriter(String postWriter) {
        SalesPostEntity salesPostEntity = salesPostRepository
                .findLatestSalesPostByPostWriter(postWriter);
        return salesPostEntity;
    }

    @Override
    public List<SalesPostEntity> readAllByWriter(String postWriter) {
        List<SalesPostEntity> salesPostEntityList = salesPostRepository.findAllByPostWriter(postWriter);
        return salesPostEntityList;
    }

    @Override
    public List<SalesPostEntity> readAll() {
        List<SalesPostEntity> salesPostEntityList = salesPostRepository.findAll();
        return salesPostEntityList;
    }

    @Override
    public boolean update(SalesPostEntity salesPostEntity) {
        if (salesPostRepository.existsByPostNumber(salesPostEntity.getPostNumber())) {
            SalesPostEntity oldSalesPostEntity = salesPostRepository.findByPostNumber(salesPostEntity.getPostNumber());
            salesPostEntity.setCategory(Optional.ofNullable(salesPostEntity.getCategory()).orElse(oldSalesPostEntity.getCategory()));
            salesPostEntity.setPostTitle(Optional.ofNullable(salesPostEntity.getPostTitle()).orElse(oldSalesPostEntity.getPostTitle()));
            salesPostEntity.setPostWriter(Optional.ofNullable(salesPostEntity.getPostWriter()).orElse(oldSalesPostEntity.getPostWriter()));
            salesPostEntity.setPostDate(Optional.ofNullable(salesPostEntity.getPostDate()).orElse(oldSalesPostEntity.getPostDate()));
            salesPostEntity.setPostContents(Optional.ofNullable(salesPostEntity.getPostContents()).orElse(oldSalesPostEntity.getPostContents()));
            salesPostEntity.setPostHitCount(salesPostEntity.getPostHitCount() == 0 ? oldSalesPostEntity.getPostHitCount() : salesPostEntity.getPostHitCount());
            salesPostEntity.setPostLike(salesPostEntity.getPostLike() == 0 ? oldSalesPostEntity.getPostLike() : salesPostEntity.getPostLike());
            salesPostEntity.setStoreLocation(Optional.ofNullable(salesPostEntity.getStoreLocation()).orElse(oldSalesPostEntity.getStoreLocation()));
            salesPostRepository.save(salesPostEntity);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean delete(Long postNumber) {
        salesPostRepository.deleteById(postNumber);
        if(salesPostRepository.existsByPostNumber(postNumber)){
            return false;
        } else{
            return true;
        }
    }
}
