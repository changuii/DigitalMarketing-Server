package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

public interface InquiryDao {
    String create(InquiryEntity inquiryEntity);
    Map<String, Object> readRecentByWriter(String inquiryWriter);
    Map<String, Object> readAllByWriter(String inquiryWriter);
    Map<String, Object> readAll();
    String update(InquiryEntity inquiryEntity);
    String delete(Long inquiryNumber);
}