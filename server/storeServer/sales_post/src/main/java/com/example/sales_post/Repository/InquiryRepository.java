package com.example.sales_post.Repository;

import com.example.sales_post.Entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    @Query(value = "SELECT * FROM inquiry_entity i " +
            "WHERE i.inquiry_writer = ?2 " +
            "AND i.post_number = ?1 " +
            "ORDER BY i.inquiry_number DESC LIMIT 1", nativeQuery = true)
    InquiryEntity findLatestInquiryByWriterAndPostNumber(Long postNumber, String inquiryWriter);
    List<InquiryEntity> findAllByInquiryWriter(String inquiryWriter);
}
