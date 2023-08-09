//package com.example.sales_post.Controller;
//
//import org.json.simple.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class kafkaController {
//
//    private final KafkaTemplate<String, JSONObject> kafkaTemplate;
//    private final Logger logger = LoggerFactory.getLogger(kafkaController.class);
//    private final InquiryController inquiryController;
//
//    public kafkaController(@Autowired KafkaTemplate<String, JSONObject> kafkaTemplate,
//    @Autowired InquiryController inquiryController) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.inquiryController = inquiryController;
//    }
//
//
//    @KafkaListener(topics = "exam10", groupId = "foo")
//    public void getMessage(JSONObject jsonObject){
//        logger.info(jsonObject.toString());
////        inquiryController.postInquiry(jsonObject);
//    }
//}
