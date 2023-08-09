package com.example.sales_post.Repository;

import com.example.sales_post.Entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    InquiryEntity findTopByInquiryAuthorOrderByInquiryNumberDesc(String inquiryAuthor);
    List<InquiryEntity> findAllByInquiryAuthor(String inquiryAuthor);
}
