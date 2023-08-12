package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class InquiryDaoImpl implements InquiryDao{
    private final InquiryRepository inquiryRepository;

    public InquiryDaoImpl(@Autowired InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public String create(InquiryEntity inquiryEntity) {
        inquiryRepository.save(inquiryEntity);
        if(inquiryRepository.existsByInquiryNumber(inquiryEntity.getInquiryNumber())){
            return "success";
        } else{
            return "fail";
        }
    }

    @Override
    public InquiryEntity readRecentByWriter(Long postNumber, String inquiryWriter) {
        InquiryEntity inquiryEntity = inquiryRepository
                .findLatestInquiryByWriterAndPostNumber(postNumber, inquiryWriter);
        return inquiryEntity;
    }

    @Override
    public List<InquiryEntity> readAllByWriter(Long postNumber, String inquiryWriter) {
        List<InquiryEntity> inquiryEntityList = inquiryRepository
                .findAllByInquiryWriter(inquiryWriter);
        return inquiryEntityList;
    }

    @Override
    public List<InquiryEntity> readAll() {
        List<InquiryEntity> inquiryEntityList = inquiryRepository
                .findAll();
        return inquiryEntityList;
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
        } else{
            return "fail";
        }
    }

    @Override
    public String delete(Long inquiryNumber) {
        inquiryRepository.deleteById(inquiryNumber);
        if(inquiryRepository.existsByInquiryNumber(inquiryNumber)){
            return "success";
        } else{
            return "fail";
        }
    }
}