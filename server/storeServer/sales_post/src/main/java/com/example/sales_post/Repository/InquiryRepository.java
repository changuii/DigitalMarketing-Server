package com.example.sales_post.Repository;

import com.example.sales_post.Entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    InquiryEntity findByInquiryNumber(Long inquiryNumber);
    boolean existsByInquiryNumber(long inquiryNumber);
    @Query(value = "SELECT * FROM Inquiry WHERE inquiryWriter = ?1 ORDER BY inquiryNumber DESC LIMIT 1", nativeQuery = true)
    InquiryEntity findLatestInquiryByWriterAndPostNumber(String inquiryWriter);

    @Query(value = "SELECT * FROM Inquiry WHERE inquiryWriter = ?1", nativeQuery = true)
    List<InquiryEntity> findAllByInquiryWriter(String inquiryWriter);
}
