package com.example.sales_post.Service;

import com.example.sales_post.DAO.InquiryDaoImpl;
import com.example.sales_post.Entity.InquiryEntity;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class InquiryServiceImpl implements InquriyService {

    private final InquiryDaoImpl inquiryDaoImpl;

    public InquiryServiceImpl(@Autowired InquiryDaoImpl inquiryDaoImpl){
        this.inquiryDaoImpl = inquiryDaoImpl;
    }

    public JSONObject createInquiry(JSONObject jsonObject){
        InquiryEntity inquiryEntity = jsonToEntity(jsonObject);
        boolean result = inquiryDaoImpl.createInquiry(inquiryEntity);
        return resultJsonObject(result);
    }

    public JSONObject readRecentInquiry(JSONObject jsonObject){
        JSONObject resultJsonObject;
        InquiryEntity inquiryEntity = inquiryDaoImpl
                .readRecentInquiry((String) jsonObject.get("inquiryAuthor"));
        if (inquiryEntity != null) {
            resultJsonObject = resultJsonObject(true, inquiryEntity);
        } else{
            resultJsonObject = resultJsonObject(false);
        }
        return resultJsonObject;
    }

    public List<JSONObject> readAllInquiry(JSONObject jsonObject) {
        List<InquiryEntity> inquiryEntityList = inquiryDaoImpl
                .readAllInquiry((String) jsonObject.get("inquiryAuthor"));
        List<JSONObject> jsonObjectList = new ArrayList<>();

        if (inquiryEntityList == null || inquiryEntityList.isEmpty()) {
            JSONObject resultJsonObject = resultJsonObject(false);
            jsonObjectList.add(resultJsonObject);
        } else{
            for (InquiryEntity entity : inquiryEntityList) {
                JSONObject resultJsonObject = resultJsonObject(true, entity);
                jsonObjectList.add(resultJsonObject);
            }
        }
        return jsonObjectList;
    }

    public JSONObject updateInquiry(JSONObject jsonObject){
        InquiryEntity inquiryEntity = jsonToEntity(jsonObject);
        boolean result = inquiryDaoImpl.updateInquiry(inquiryEntity);
        return resultJsonObject(result);
    }

    public JSONObject deleteInquiry(JSONObject jsonObject){
        String inquiryNumberStr = (String) jsonObject.get("inquiryNumber");
        Long inquiryNumber = Long.valueOf(inquiryNumberStr);
        boolean result = inquiryDaoImpl.deleteInquiry(inquiryNumber);
        return resultJsonObject(result);
    }

    public InquiryEntity jsonToEntity(JSONObject jsonObject){
        String inquiryNumberStr = (String) jsonObject.get("inquiryNumber");
        Long inquiryNumber = Long.valueOf(inquiryNumberStr);

        InquiryEntity inquiryEntity = InquiryEntity.builder()
                .inquiryNumber(inquiryNumber)
                .inquiryAuthor((String) jsonObject.get("inquiryAuthor"))
                .inquiryContents((String) jsonObject.get("inquiryContents"))
                .build();
        return inquiryEntity;
    }

    public JSONObject entityToJson(InquiryEntity inquiryEntity){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("inquiryNumber", inquiryEntity.getInquiryNumber());
        jsonObject.put("inquiryAuthor", inquiryEntity.getInquiryAuthor());
        jsonObject.put("inquiryContents", inquiryEntity.getInquiryContents());
        return jsonObject;
    }

    public JSONObject resultJsonObject(boolean result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject;
    }

    public JSONObject resultJsonObject(boolean result, InquiryEntity inquiryEntity){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("inquiryNumber", inquiryEntity.getInquiryNumber());
        jsonObject.put("inquiryAuthor", inquiryEntity.getInquiryAuthor());
        jsonObject.put("inquiryContents", inquiryEntity.getInquiryContents());
        jsonObject.put("result", result);
        return jsonObject;
    }
}
