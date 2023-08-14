package com.example.sales_post.DAO;

import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.SalesPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SalesPostDaoImpl implements SalesPostDao {
    private final SalesPostRepository salesPostRepository;
    private  final GlobalValidCheck globalValidCheck;

    public SalesPostDaoImpl(@Autowired SalesPostRepository salesPostRepository,
                            @Autowired GlobalValidCheck globalValidCheck) {
        this.salesPostRepository = salesPostRepository;
        this.globalValidCheck = globalValidCheck;
    }

    @Transactional()
    @Override
    public String create(SalesPostEntity salesPostEntity) {
        Long postNumber = salesPostEntity.getPostNumber();
        String valid = globalValidCheck.validCheck(salesPostEntity);

        if (valid.equals("success")) {
            salesPostEntity.setPostDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            this.salesPostRepository.save(salesPostEntity);
        }
        return valid;
    }

    @Transactional()
    @Override
    public Map<String, Object> read(Long postNumber) {
        SalesPostEntity salesPostEntity = salesPostRepository.findByPostNumber(postNumber);
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntity == null) {
            result.put("result", "Error: SalesPost not found");
        } else {
            result.put("data", salesPostEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Transactional()
    @Override
    public Map<String, Object> readRecentByWriter(String postWriter) {
        SalesPostEntity salesPostEntity = salesPostRepository.findLatestSalesPostByPostWriter(postWriter);
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntity == null) {
            result.put("result", "Error: SalesPost not found");
        } else {
            result.put("data", salesPostEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Transactional()
    @Override
    public Map<String, Object> readAllByWriter(String postWriter) {
        List<SalesPostEntity> salesPostEntityList = salesPostRepository.findAllByPostWriter(postWriter);
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntityList.isEmpty()) {
            result.put("result", "Error: No SalesPosts found for writer");
        } else {
            result.put("data", salesPostEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Transactional()
    @Override
    public Map<String, Object> readAll() {
        List<SalesPostEntity> salesPostEntityList = salesPostRepository.findAll();
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntityList.isEmpty()) {
            result.put("result", "Error: No salesPosts found");
        } else {
            result.put("data", salesPostEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Transactional()
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
            salesPostEntity.setInquiries(oldSalesPostEntity.getInquiries());
            salesPostEntity.setProducts(oldSalesPostEntity.getProducts());
            salesPostEntity.setReviews(oldSalesPostEntity.getReviews());
            salesPostRepository.save(salesPostEntity);
            return "success";
        } else{
            return "Error: SalesPost not found";
        }
    }

    @Transactional()
    @Override
    public String delete(Long postNumber) {
        if(salesPostRepository.existsByPostNumber(postNumber)){
            salesPostRepository.deleteByPostNumber(postNumber);
            return "success";
        } else{
            return "Error: SalesPost not found";
        }

    }
}