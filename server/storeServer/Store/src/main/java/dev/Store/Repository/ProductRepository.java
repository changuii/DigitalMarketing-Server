package dev.Store.Repository;

import dev.Store.Entity.ProductEntity;
import dev.Store.Entity.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
//    ProductEntity findByNumber(Long number);
//    boolean existsByNumber(Long number);
    ProductEntity findById(ProductId id);
    boolean existsById(ProductId id);
}
