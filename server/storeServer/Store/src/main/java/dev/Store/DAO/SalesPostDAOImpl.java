package dev.Store.DAO;

import dev.Store.Entity.SalesPostEntity;
import dev.Store.Repository.SalesPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SalesPostDAOImpl implements SalesPostDAO{
    @Autowired
    private SalesPostRepository salesPostRepository;

    @Override
    public String create(SalesPostEntity salesPostEntity) {
        salesPostEntity.setPostDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.salesPostRepository.save(salesPostEntity);

        if (salesPostRepository.existsById(salesPostEntity.getPostNumber())) {
            return "success";
        }
        return "Error: SalesPost가 올바르게 저장되지 않았습니다.";
    }

    @Override
    public Map<String, Object> readAll() {
        List<SalesPostEntity> salesPostEntityList = salesPostRepository.findAll();
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntityList.isEmpty()) {
            result.put("result", "Error: 저장된 SalesPost가 없습니다.");
        } else {
            result.put("data", salesPostEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public Map<String, Object> readById(Long postNumber) {
        SalesPostEntity salesPostEntity = salesPostRepository.findByPostNumber(postNumber);
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntity == null) {
            result.put("result", "Error: 해당 하는 SalesPost가 존재하지 않습니다.");
        } else {
            result.put("data", salesPostEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public String update(SalesPostEntity salesPostEntity) {
        if (salesPostRepository.existsById(salesPostEntity.getPostNumber())) {
            SalesPostEntity oldSalesPostEntity = salesPostRepository.findByPostNumber(salesPostEntity.getPostNumber());
            salesPostEntity.setCategory(Optional.ofNullable(salesPostEntity.getCategory()).orElse(oldSalesPostEntity.getCategory()));
            salesPostEntity.setPostTitle(Optional.ofNullable(salesPostEntity.getPostTitle()).orElse(oldSalesPostEntity.getPostTitle()));
            salesPostEntity.setPostWriter(Optional.ofNullable(salesPostEntity.getPostWriter()).orElse(oldSalesPostEntity.getPostWriter()));
            salesPostEntity.setPostDate(Optional.ofNullable(salesPostEntity.getPostDate()).orElse(oldSalesPostEntity.getPostDate()));
            salesPostEntity.setPostContents(Optional.ofNullable(salesPostEntity.getPostContents()).orElse(oldSalesPostEntity.getPostContents()));
            salesPostEntity.setPostHitCount(Optional.ofNullable(salesPostEntity.getPostHitCount()).orElse(oldSalesPostEntity.getPostHitCount()));
            salesPostEntity.setPostLike(Optional.ofNullable(salesPostEntity.getPostLike()).orElse(oldSalesPostEntity.getPostLike()));
            salesPostEntity.setStoreLocation(Optional.ofNullable(salesPostEntity.getStoreLocation()).orElse(oldSalesPostEntity.getStoreLocation()));
            salesPostEntity.setMainImage(Optional.ofNullable(salesPostEntity.getMainImage()).orElse(oldSalesPostEntity.getMainImage()));
            salesPostEntity.setDescImages(Optional.ofNullable(salesPostEntity.getDescImages()).orElse(oldSalesPostEntity.getDescImages()));
            salesPostEntity.setProducts(oldSalesPostEntity.getProducts());
            salesPostRepository.save(salesPostEntity);
            return "success";
        } else {
            return "Error: 수정하려고 하는 SalesPost가 존재하지 않습니다.";
        }
    }

    @Override
    public String delete(Long postNumber) {
        if(salesPostRepository.existsById(postNumber)){
            salesPostRepository.existsById(postNumber);
            return "success";
        } else{
            return "Error: 삭제하려고 하는 SalesPost가 존재하지 않습니다.";
        }
    }
}
