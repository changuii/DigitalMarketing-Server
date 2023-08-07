package com.example.sales_post.Service;

import com.example.sales_post.Entity.InquiryEntity;
import com.example.sales_post.Repository.InquiryRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InquiryServiceImpl implements InquriyService {

    private final InquiryRepository inquiryRepository;

    public InquiryServiceImpl(
            @Autowired InquiryRepository inquiryRepository){
        this.inquiryRepository = inquiryRepository;
    }

    public boolean createInquiry(JSONObject jsonObject){
        InquiryEntity inquiryEntity = new InquiryEntity();
        String inquiryNumberStr = (String) jsonObject.get("inquiryNumber");
        Long inquiryNumber = Long.valueOf(inquiryNumberStr);
        inquiryEntity.setInquiryNumber(inquiryNumber);
        inquiryEntity.setInquiryAuthor((String) jsonObject.get("inquiryAuthor"));
        inquiryEntity.setInquiryContents((String) jsonObject.get("inquiryContents"));
        inquiryRepository.save(inquiryEntity);
        return true;
    }

    public JSONObject readInquiry(String author){
        JSONObject jsonObject = new JSONObject();
        InquiryEntity inquiryEntity = inquiryRepository.findByInquiryAuthor(author);
        jsonObject.put("inquiryNumber", inquiryEntity.getInquiryNumber());
        jsonObject.put("inquiryAuthor", inquiryEntity.getInquiryAuthor());
        jsonObject.put("inquiryContents", inquiryEntity.getInquiryContents());
        return jsonObject;
    }

    public JSONObject readRecentInquiry(String author){
        JSONObject jsonObject = new JSONObject();
        InquiryEntity inquiryEntity = inquiryRepository.findTopByInquiryAuthorOrderByInquiryNumberDesc(author);
        if (inquiryEntity != null) {
            jsonObject.put("inquiryNumber", inquiryEntity.getInquiryNumber());
            jsonObject.put("inquiryAuthor", inquiryEntity.getInquiryAuthor());
            jsonObject.put("inquiryContents", inquiryEntity.getInquiryContents());
        } else {
            jsonObject.put("message", "No recent inquiry found for the author");
        }
        return jsonObject;
    }

    public List<JSONObject> readAllInquiry(String author) {
        List<InquiryEntity> inquiryEntityList = inquiryRepository.findAllByInquiryAuthor(author);
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (inquiryEntityList == null || inquiryEntityList.isEmpty()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "No recent inquiry found for the author");
            jsonObjectList.add(jsonObject);
        } else{
            for (InquiryEntity entity : inquiryEntityList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("inquiryNumber", entity.getInquiryNumber());
                jsonObject.put("inquiryAuthor", entity.getInquiryAuthor());
                jsonObject.put("inquiryContents", entity.getInquiryContents());
                jsonObjectList.add(jsonObject);
            }
        }
        return jsonObjectList;
    }

    public boolean updateInquiry(JSONObject jsonObject){
        String inquiryNumberStr = (String) jsonObject.get("inquiryNumber");
        Long inquiryNumber = Long.valueOf(inquiryNumberStr);
        Optional<InquiryEntity> OpInquiryEntity = inquiryRepository.findById(inquiryNumber);
        if(OpInquiryEntity.isPresent()) {
            InquiryEntity inquiryEntity = OpInquiryEntity.get();
            inquiryEntity.setInquiryAuthor((String) jsonObject.get("inquiryAuthor"));
            inquiryEntity.setInquiryContents((String) jsonObject.get("inquiryContents"));
            inquiryRepository.save(inquiryEntity);
            return true;
        }
        return false;
    }

    public void deletePost(Long id){
        inquiryRepository.deleteById(id);
    }
}
