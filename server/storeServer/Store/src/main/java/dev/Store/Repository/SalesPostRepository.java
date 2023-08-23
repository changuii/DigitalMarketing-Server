package dev.Store.Repository;

import dev.Store.Entity.SalesPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface SalesPostRepository extends JpaRepository<SalesPostEntity, Long> {
    SalesPostEntity findBySalesPostNumber(Long salesPostNumber);
    boolean existsBySalesPostNumber(Long salesPostNumber);
    List<SalesPostEntity> findByCategory(String category);
}
