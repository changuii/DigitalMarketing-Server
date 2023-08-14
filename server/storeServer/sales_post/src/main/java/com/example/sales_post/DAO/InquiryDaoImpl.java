package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.InquiryRepository;
import com.example.sales_post.Service.InquiryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InquiryDaoImpl implements InquiryDao {
    private final InquiryRepository inquiryRepository;
    private final GlobalValidCheck globalValidCheck;
    private final Logger logger = LoggerFactory.getLogger(InquiryServiceImpl.class);

    public InquiryDaoImpl(@Autowired InquiryRepository inquiryRepository,
                          @Autowired GlobalValidCheck globalValidCheck) {
        this.inquiryRepository = inquiryRepository;
        this.globalValidCheck = globalValidCheck;
    }

    @Transactional()
    @Override
    public String create(InquiryEntity inquiryEntity) {
        Long inquiryNumber = inquiryEntity.getInquiryNumber();
        String valid = globalValidCheck.validCheck(inquiryEntity);

        if (valid.equals("success")) {
            inquiryEntity.setInquiryDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            inquiryRepository.save(inquiryEntity);

            SalesPostEntity salesPostEntity = inquiryEntity.getSalesPostEntity();

            if (salesPostEntity != null) {
                salesPostEntity.addInquiry(inquiryEntity);
            }
        }
        return valid;
    }

    @Transactional()
    @Override
    public Map<String, Object> readRecentByWriter(String inquiryWriter) {
        InquiryEntity inquiryEntity = inquiryRepository.findLatestInquiryByWriterAndPostNumber(inquiryWriter);
        Map<String, Object> result = new HashMap<>();

        if (inquiryEntity == null) {
            result.put("result", "Error: Inquiry not found");
        } else {
            result.put("data", inquiryEntity);
            result.put("result", "success");
        }
        return result;
    }

    @Transactional()
    @Override
    public Map<String, Object> readAllByWriter(String inquiryWriter) {
        List<InquiryEntity> inquiryEntityList = inquiryRepository.findAllByInquiryWriter(inquiryWriter);
        Map<String, Object> result = new HashMap<>();

        logger.info("dao: "+inquiryEntityList.toString());
        if (inquiryEntityList.isEmpty()) {
            result.put("result", "Error: No inquiries found for writer");
        } else {
            result.put("data", inquiryEntityList);
            result.put("result", "success");
        }
        return result;
    }

    @Transactional()
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

    @Transactional()
    @Override
    public String update(InquiryEntity inquiryEntity) {
        if (inquiryRepository.existsByInquiryNumber(inquiryEntity.getInquiryNumber())) {
            InquiryEntity oldInquiryEntity = inquiryRepository.findByInquiryNumber(inquiryEntity.getInquiryNumber());
            inquiryEntity.setInquiryWriter(Optional.ofNullable(inquiryEntity.getInquiryWriter()).orElse(oldInquiryEntity.getInquiryWriter()));
            inquiryEntity.setInquiryContents(Optional.ofNullable(inquiryEntity.getInquiryContents()).orElse(oldInquiryEntity.getInquiryContents()));
            inquiryEntity.setInquiryDate(Optional.ofNullable(inquiryEntity.getInquiryDate()).orElse(oldInquiryEntity.getInquiryDate()));
            inquiryEntity.setSalesPostEntity(Optional.ofNullable(inquiryEntity.getSalesPostEntity()).orElse(oldInquiryEntity.getSalesPostEntity()));

            // 연관된 SalesPostEntity와의 연관 관계 설정
            SalesPostEntity salesPostEntity = inquiryEntity.getSalesPostEntity();
            if (salesPostEntity != null) {
                salesPostEntity.addInquiry(inquiryEntity);
            }

            inquiryRepository.save(inquiryEntity);
            return "success";
        } else {
            return "Error: Inquiry not found";
        }
    }

    @Transactional()
    @Override
    public String delete(Long inquiryNumber) {
        if (inquiryRepository.existsByInquiryNumber(inquiryNumber)) {
            InquiryEntity inquiryEntity = inquiryRepository.findByInquiryNumber(inquiryNumber);

            // 연관된 SalesPostEntity의 inquiries 컬렉션에서 삭제
            SalesPostEntity salesPostEntity = inquiryEntity.getSalesPostEntity();
            if (salesPostEntity != null) {
                salesPostEntity.removeInquiry(inquiryEntity);
            }

            inquiryRepository.deleteById(inquiryNumber);
            return "success";
        } else {
            return "Error: Inquiry not found";
        }
    }
}
