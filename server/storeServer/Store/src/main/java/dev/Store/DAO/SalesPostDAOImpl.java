package dev.Store.DAO;

import dev.Store.Entity.Comment;
import dev.Store.Entity.ImageData;
import dev.Store.Entity.Product;
import dev.Store.Entity.SalesPostEntity;
import dev.Store.Repository.CommentRepository;
import dev.Store.Repository.SalesPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class SalesPostDAOImpl implements SalesPostDAO{
    private SalesPostRepository salesPostRepository;
    private CommentRepository commentRepository;

    public SalesPostDAOImpl(@Autowired SalesPostRepository salesPostRepository,
                            @Autowired CommentRepository commentRepository) {
        this.salesPostRepository = salesPostRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Map<String, Object> create(SalesPostEntity salesPostEntity) {
        salesPostEntity.setPostDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.salesPostRepository.save(salesPostEntity);
        Map<String, Object> result = new HashMap<>();

        if (salesPostRepository.existsBySalesPostNumber(salesPostEntity.getSalesPostNumber())) {
            result.put("result", "success");
            result.put("salesPostNumber", salesPostEntity.getSalesPostNumber());
        } else{
            result.put("result", "Error: SalesPost가 올바르게 저장되지 않았습니다.");
        }
        return result;
    }

    @Override
    public String createPostComment(Long salesPostNumber, Comment comment){
        SalesPostEntity salesPostEntity = salesPostRepository.findBySalesPostNumber(salesPostNumber);

        if (salesPostEntity != null) {
            comment.setCommentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            salesPostEntity.add(comment);
            this.salesPostRepository.save(salesPostEntity);
            return "success";
        }
        return "Error: Comment가 올바르게 저장되지 않았습니다.";
    }

    @Override
    public Map<String, Object> readByWriterAndTitle(Long salesPostNumber) {
        SalesPostEntity salesPostEntity = salesPostRepository.findBySalesPostNumber(salesPostNumber);
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntity == null) {
            result.put("result", "Error: 해당하는 SalesPost가 존재하지 않습니다.");
        } else {
            result.put("data", salesPostEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public Map<String, Object> readAllByCategory(String category) {
        List<SalesPostEntity> salesPostEntityList = salesPostRepository.findByCategory(category);
        Map<String, Object> result = new HashMap<>();

        if (salesPostEntityList.isEmpty()) {
            result.put("result", "Error: 선택한 카테고리에 SalesPost가 존재하지 않습니다.");
        } else {
            result.put("data", salesPostEntityList);
            result.put("result", "success");
        }
        return result;
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
    public String update(SalesPostEntity salesPostEntity) {
        SalesPostEntity oldSalesPostEntity = salesPostRepository.findBySalesPostNumber(salesPostEntity.getSalesPostNumber());

        if (oldSalesPostEntity != null) {
            salesPostEntity.setSalesPostNumber(Optional.ofNullable(salesPostEntity.getSalesPostNumber()).orElse(oldSalesPostEntity.getSalesPostNumber()));
            salesPostEntity.setCategory(Optional.ofNullable(salesPostEntity.getCategory()).orElse(oldSalesPostEntity.getCategory()));
            salesPostEntity.setPostTitle(Optional.ofNullable(salesPostEntity.getPostTitle()).orElse(oldSalesPostEntity.getPostTitle()));
            salesPostEntity.setPostWriter(Optional.ofNullable(salesPostEntity.getPostWriter()).orElse(oldSalesPostEntity.getPostWriter()));
            salesPostEntity.setPostDate(Optional.ofNullable(salesPostEntity.getPostDate()).orElse(oldSalesPostEntity.getPostDate()));
            salesPostEntity.setPostContents(Optional.ofNullable(salesPostEntity.getPostContents()).orElse(oldSalesPostEntity.getPostContents()));
            salesPostEntity.setPostHitCount(Optional.ofNullable(salesPostEntity.getPostHitCount()).orElse(oldSalesPostEntity.getPostHitCount()));
            salesPostEntity.setPostLike(Optional.ofNullable(salesPostEntity.getPostLike()).orElse(oldSalesPostEntity.getPostLike()));
            salesPostEntity.setStoreLocation(Optional.ofNullable(salesPostEntity.getStoreLocation()).orElse(oldSalesPostEntity.getStoreLocation()));
            salesPostEntity.setMainImage(Optional.ofNullable(salesPostEntity.getMainImage()).orElse(oldSalesPostEntity.getMainImage()));
//            salesPostEntity.setDescImages(Optional.ofNullable(salesPostEntity.getDescImages()).orElse(oldSalesPostEntity.getDescImages()));
//            salesPostEntity.setProducts(Optional.ofNullable(salesPostEntity.getProducts()).orElse(oldSalesPostEntity.getProducts()));

//            List<ImageData> images = new ArrayList<>();
//            for(ImageData image : oldSalesPostEntity.getDescImages()){
//                images.add(image);
//            }
//            salesPostEntity.setDescImages(images);
//
//            List<Product> newProducts = new ArrayList<>();
//            for (Product product : oldSalesPostEntity.getProducts()) {
//                newProducts.add(product);
//            }

            List<ImageData> images = new ArrayList<>(oldSalesPostEntity.getDescImages());
            salesPostEntity.setDescImages(images);

            List<Product> newProducts = new ArrayList<>(oldSalesPostEntity.getProducts());
            salesPostEntity.setProducts(newProducts);

            salesPostRepository.save(salesPostEntity);
            return "success";
        } else {
            return "Error: 수정하려고 하는 SalesPost가 존재하지 않습니다.";
        }
    }

    @Override
    public Map<String, Object> postLikeUpdate(Long salesPostNumber, String action){
        SalesPostEntity salesPostEntity = salesPostRepository.findBySalesPostNumber(salesPostNumber);
        Map<String, Object> result = new HashMap<>();

        if(salesPostEntity != null){
            if(action.equals("disLike")){
                salesPostEntity.setPostLike(salesPostEntity.getPostLike()-1);
            }else {
                salesPostEntity.setPostLike(salesPostEntity.getPostLike()+1);
            }
            result.put("data", salesPostEntity.getPostLike());
            result.put("result", "success");
        } else{
            result.put("result", "해당하는 SalesPost가 존재하지 않습니다.");
        }
        return result;
    }

    @Override
    public Map<String, Object> postHitCountUpdate(Long salesPostNumber){
        SalesPostEntity salesPostEntity = salesPostRepository.findBySalesPostNumber(salesPostNumber);
        Map<String, Object> result = new HashMap<>();

        if(salesPostEntity != null){
            salesPostEntity.setPostHitCount(salesPostEntity.getPostHitCount()+1);
            result.put("data", salesPostEntity.getPostHitCount());
            result.put("result", "success");
        } else{
            result.put("result", "해당하는 SalesPost가 존재하지 않습니다.");
        }
        return result;
    }

    @Override
    public String delete(Long salesPostNumber) {
        SalesPostEntity salesPostEntity = salesPostRepository.findBySalesPostNumber(salesPostNumber);

        if(salesPostEntity != null){
            salesPostRepository.delete(salesPostEntity);
            return "success";
        } else{
            return "Error: 삭제하려고 하는 SalesPost가 존재하지 않습니다.";
        }
    }

    @Override
    public String deleteComment(Long salesPostNumber, String commentWriter){
        SalesPostEntity salesPostEntity = salesPostRepository.findBySalesPostNumber(salesPostNumber);
        Comment comment = salesPostEntity.findCommentByCommentWriter(commentWriter);
        if(salesPostEntity != null && comment != null){
            salesPostEntity.remove(comment);
            salesPostRepository.save(salesPostEntity);
            return "success";
        } else{
            return "Error: 삭제하려고 하는 Comment가 존재하지 않습니다.";
        }
    }
}
