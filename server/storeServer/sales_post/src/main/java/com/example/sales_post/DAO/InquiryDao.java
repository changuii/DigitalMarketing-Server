package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface InquiryDao {
    String create(InquiryEntity inquiryEntity);
    InquiryEntity readRecentByWriter(Long postNumber, String inquiryWriter);
    List<InquiryEntity> readAllByWriter(Long postNumber, String inquiryWriter);
    List<InquiryEntity> readAll();
    String update(InquiryEntity inquiryEntity);
    String delete(Long inquiryNumber);
}