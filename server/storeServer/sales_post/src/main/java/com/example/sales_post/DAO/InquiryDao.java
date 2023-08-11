package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface InquiryDao {
    boolean create(InquiryEntity inquiryEntity);
    InquiryEntity readRecentByWriter(Long postNumber, String inquiryWriter);
    List<InquiryEntity> readAllByWriter(Long postNumber, String inquiryWriter);
    List<InquiryEntity> readAll();
    boolean update(InquiryEntity inquiryEntity);
    boolean delete(Long inquiryNumber);
}
