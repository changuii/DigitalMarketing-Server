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
    public boolean createInquiry(InquiryEntity inquiryEntity) {
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
    public InquiryEntity readRecentInquiry(String author) {
        InquiryEntity inquiryEntity = inquiryRepository
                .findTopByInquiryAuthorOrderByInquiryNumberDesc(author);
        return inquiryEntity;
    }

    @Override
    public List<InquiryEntity> readAllInquiry(String author) {
        List<InquiryEntity> inquiryEntityList = inquiryRepository
                .findAllByInquiryAuthor(author);
        return inquiryEntityList;
    }

    @Override
    public boolean updateInquiry(InquiryEntity inquiryEntity) {
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
    public boolean deleteInquiry(Long id) {
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
