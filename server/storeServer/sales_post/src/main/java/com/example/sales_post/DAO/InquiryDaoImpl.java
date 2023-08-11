package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class InquiryDaoImpl implements InquiryDao{
    private final InquiryRepository inquiryRepository;

    public InquiryDaoImpl(@Autowired InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public boolean create(InquiryEntity inquiryEntity) {
        inquiryRepository.save(inquiryEntity);
        if(inquiryRepository.existsByInquiryNumber(inquiryEntity.getInquiryNumber())){
            return true;
        } else{
            return false;
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
    public boolean update(InquiryEntity inquiryEntity) {
        if(inquiryRepository.existsByInquiryNumber(inquiryEntity.getInquiryNumber())){
            inquiryRepository.save(inquiryEntity);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean delete(Long inquiryNumber) {
        inquiryRepository.deleteById(inquiryNumber);
        if(inquiryRepository.existsByInquiryNumber(inquiryNumber)){
            return false;
        } else{
            return true;
        }
    }
}