package com.example.sales_post.DAO;

import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.SalesPostRepository;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
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
    public String create(SalesPostEntity salesPostEntity) {
        salesPostRepository.save(salesPostEntity);
        if (salesPostRepository.existsByPostNumber(salesPostEntity.getPostNumber())) {
            return "success";
        } else {
            return "fail";
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
    public String update(SalesPostEntity salesPostEntity) {
        if (salesPostRepository.existsByPostNumber(salesPostEntity.getPostNumber())) {
            SalesPostEntity oldSalesPostEntity = salesPostRepository.findByPostNumber(salesPostEntity.getPostNumber());
            salesPostEntity.setCategory(Optional.ofNullable(salesPostEntity.getCategory()).orElse(oldSalesPostEntity.getCategory()));
            salesPostEntity.setPostTitle(Optional.ofNullable(salesPostEntity.getPostTitle()).orElse(oldSalesPostEntity.getPostTitle()));
            salesPostEntity.setPostWriter(Optional.ofNullable(salesPostEntity.getPostWriter()).orElse(oldSalesPostEntity.getPostWriter()));
            salesPostEntity.setPostDate(Optional.ofNullable(salesPostEntity.getPostDate()).orElse(oldSalesPostEntity.getPostDate()));
            salesPostEntity.setPostContents(Optional.ofNullable(salesPostEntity.getPostContents()).orElse(oldSalesPostEntity.getPostContents()));
            salesPostEntity.setPostHitCount(Optional.ofNullable(salesPostEntity.getPostHitCount()).orElse(oldSalesPostEntity.getPostHitCount()));
            salesPostEntity.setPostLike(Optional.ofNullable(salesPostEntity.getPostLike()).orElse(oldSalesPostEntity.getPostLike()));
            salesPostEntity.setStoreLocation(Optional.ofNullable(salesPostEntity.getStoreLocation()).orElse(oldSalesPostEntity.getStoreLocation()));
            salesPostRepository.save(salesPostEntity);
            return "success";
        } else{
            return "fail";
        }
    }

    @Override
    public String delete(Long postNumber) {
        salesPostRepository.deleteById(postNumber);
        if(salesPostRepository.existsByPostNumber(postNumber)){
            return "success";
        } else{
            return "fail";
        }
    }
}