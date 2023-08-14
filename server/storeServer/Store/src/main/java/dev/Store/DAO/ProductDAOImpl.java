package dev.Store.DAO;

import dev.Store.Entity.ProductEntity;
import dev.Store.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dev.Store.Entity.SalesPostEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDAOImpl implements ProductDAO{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public String create(ProductEntity productEntity) {
        this.productRepository.save(productEntity);

        if (productRepository.existsById(productEntity.getNumber())) {
            SalesPostEntity salesPostEntity = productEntity.getSalesPostEntity();

            if (salesPostEntity != null) {
                salesPostEntity.addProduct(productEntity);
            }

            return "scuccess";
        }
        return "Error: Product가 올바르게 저장되지 않았습니다.";
    }

    @Override
    public Map<String, Object> readAll() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        Map<String, Object> result = new HashMap<>();

        if (productEntityList.isEmpty()) {
            result.put("result", "Error: 저장된 Product가 없습니다.");
        } else {
            result.put("data", productEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public Map<String, Object> readByNumber(Long number) {
        ProductEntity productEntity = productRepository.findByNumber(number);
        Map<String, Object> result = new HashMap<>();
        if (productEntity == null) {
            result.put("result", "Error: 해당 하는 Product가 존재하지 않습니다.");
        } else {
            result.put("data", productEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public String update(ProductEntity productEntity) {
        Long number = productEntity.getNumber();

        if(productRepository.existsById(number)) {
            ProductEntity oldProductEntity = productRepository.findByNumber(number);
            productEntity.setDetail(Optional.ofNullable(productEntity.getDetail()).orElse(oldProductEntity.getDetail()));
            productEntity.setPrice(Optional.ofNullable(productEntity.getPrice()).orElse(oldProductEntity.getPrice()));
            productEntity.setQuantity(Optional.ofNullable(productEntity.getQuantity()).orElse(oldProductEntity.getQuantity()));
            productEntity.setSalesPostEntity(Optional.ofNullable(productEntity.getSalesPostEntity()).orElse(oldProductEntity.getSalesPostEntity()));

            SalesPostEntity salesPostEntity = productEntity.getSalesPostEntity();
            if (salesPostEntity != null) {
                salesPostEntity.addProduct(productEntity);
            }

            productRepository.save(productEntity);
            return "success";
        } else{
            return "Error: 수정하려고 하는 Product가 존재하지 않습니다.";
        }
    }

    @Override
    public String delete(Long number) {
        if (productRepository.existsById(number)) {
            ProductEntity productEntity = productRepository.findByNumber(number);

            SalesPostEntity salesPostEntity = productEntity.getSalesPostEntity();
            if (salesPostEntity != null) {
                salesPostEntity.removeProduct(productEntity);
            }

            productRepository.deleteById(number);
            return "success";
        } else {
            return "Error: 삭제하려고 하는 Product가 존재하지 않습니다.";
        }
    }
}