package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InquiryDaoImpl implements InquiryDao {
    private final InquiryRepository inquiryRepository;

    public InquiryDaoImpl(@Autowired InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public String create(InquiryEntity inquiryEntity) {
        inquiryRepository.save(inquiryEntity);

        if (inquiryRepository.existsByInquiryNumber(inquiryEntity.getInquiryNumber())) {
            return "success";
        } else {
            return "Error: Failed to create inquiry";
        }
    }

    @Override
    public Map<String, Object> readRecentByWriter(Long postNumber, String inquiryWriter) {
        InquiryEntity inquiryEntity = inquiryRepository.findLatestInquiryByWriterAndPostNumber(postNumber, inquiryWriter);
        Map<String, Object> result = new HashMap<>();

        if (inquiryEntity == null) {
            result.put("result", "Error: Inquiry not found");
        } else {
            result.put("data", inquiryEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public Map<String, Object> readAllByWriter(Long postNumber, String inquiryWriter) {
        List<InquiryEntity> inquiryEntityList = inquiryRepository.findAllByInquiryWriter(inquiryWriter);
        Map<String, Object> result = new HashMap<>();

        if (inquiryEntityList.isEmpty()) {
            result.put("result", "Error: No inquiries found for writer");
        } else {
            result.put("data", inquiryEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public Map<String, Object> readAll() {
        List<InquiryEntity> inquiryEntityList = inquiryRepository.findAll();
        Map<String, Object> result = new HashMap<>();

        if (inquiryEntityList.isEmpty()) {
            result.put("result", "Error: No inquiries found");
        } else {
            result.put("data", inquiryEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Override
    public String update(InquiryEntity inquiryEntity) {
        if (inquiryRepository.existsByInquiryNumber(inquiryEntity.getInquiryNumber())) {
            InquiryEntity oldInquiryEntity = inquiryRepository.findByInquiryNumber(inquiryEntity.getInquiryNumber());
            inquiryEntity.setInquiryWriter(Optional.ofNullable(inquiryEntity.getInquiryWriter()).orElse(oldInquiryEntity.getInquiryWriter()));
            inquiryEntity.setInquiryContents(Optional.ofNullable(inquiryEntity.getInquiryContents()).orElse(oldInquiryEntity.getInquiryContents()));
            inquiryEntity.setSalesPostEntity(Optional.ofNullable(inquiryEntity.getSalesPostEntity()).orElse(oldInquiryEntity.getSalesPostEntity()));
            inquiryRepository.save(inquiryEntity);
            return "success";
        } else {
            return "Error: Inquiry not found";
        }
    }

    @Override
    public String delete(Long inquiryNumber) {
        if (inquiryRepository.existsByInquiryNumber(inquiryNumber)) {
            inquiryRepository.deleteById(inquiryNumber);
            return "success";
        } else {
            return "Error: Inquiry not found";
        }
    }
}
