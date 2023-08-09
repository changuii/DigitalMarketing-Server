package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface InquiryDao {
    boolean createInquiry(InquiryEntity inquiryEntity);
    InquiryEntity readRecentInquiry(String author);
    List<InquiryEntity> readAllInquiry(String author);
    boolean updateInquiry(InquiryEntity inquiryEntity);
    boolean deleteInquiry(Long inquiryNumber);
}
