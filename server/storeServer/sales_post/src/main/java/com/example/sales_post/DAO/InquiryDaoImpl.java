package com.example.sales_post.DAO;

import com.example.sales_post.Entity.InquiryEntity;
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
    public boolean create(InquiryEntity inquiryEntity) {
        inquiryRepository.save(inquiryEntity);
        Optional<InquiryEntity> oldInquiryEntity = inquiryRepository
                .findById(inquiryEntity.getInquiryNumber());
        if(oldInquiryEntity.isPresent()){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public InquiryEntity readRecentByWriter(Long postNumber, String Writer) {
        InquiryEntity inquiryEntity = inquiryRepository
                .findLatestInquiryByWriterAndPostNumber(postNumber, Writer);
        return inquiryEntity;
    }

    @Override
    public List<InquiryEntity> readAllByWriter(Long postNumber, String Writer) {
        List<InquiryEntity> inquiryEntityList = inquiryRepository
                .findAllByInquiryWriter(Writer);
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
        Optional<InquiryEntity> oldInquiryEntity = inquiryRepository
                .findById(inquiryEntity.getInquiryNumber());
        if(oldInquiryEntity.isPresent()){
            inquiryRepository.save(inquiryEntity);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        inquiryRepository.deleteById(id);
        Optional<InquiryEntity> oldInquiryEntity = inquiryRepository
                .findById(id);
        if(oldInquiryEntity.isPresent()){
            return false;
        } else{
            return true;
        }
    }
}
