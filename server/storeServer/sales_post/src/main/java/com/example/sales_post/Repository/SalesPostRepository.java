package com.example.sales_post.Repository;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesPostRepository extends JpaRepository<SalesPostEntity, Long> {
    SalesPostEntity findByPostNumber(Long postNumber);
    boolean existsByPostNumber(long postNumber);
    SalesPostEntity findLatestSalesPostByPostWriter(String postWriter);
    List<SalesPostEntity> findAllByPostWriter(String postWriter);
}
