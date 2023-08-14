package dev.Store.Repository;

import dev.Store.Entity.SalesPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPostRepository extends JpaRepository<SalesPostEntity, Long> {
    SalesPostEntity findFirstByPostWriterAndPostTitleOrderByPostDateDesc(String postWriter, String postTitle);

}
