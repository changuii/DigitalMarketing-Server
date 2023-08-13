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
//    private final KafkaTemplate<String, JSONObject> kafkaTemplate;
//    private final Logger logger = LoggerFactory.getLogger(kafkaController.class);
//    private final InquiryController inquiryController;
//    private final ProductController productController;
//    private final ReviewController reviewController;
//    private final SalesPostController salesPostController;
//
//    public kafkaController(@Autowired KafkaTemplate<String, JSONObject> kafkaTemplate,
//                           @Autowired InquiryController inquiryController,
//                           @Autowired ProductController productController,
//                           @Autowired ReviewController reviewController,
//                           @Autowired SalesPostController salesPostController) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.inquiryController = inquiryController;
//        this.productController = productController;
//        this.reviewController = reviewController;
//        this.salesPostController = salesPostController;
//    }
//
//    @KafkaListener(topics = "exam10", groupId = "foo")
//    public void getMessage(JSONObject jsonObject){
//        logger.info(jsonObject.toString());
//        actionControl(jsonObject);
//    }
//
//    public JSONObject actionControl(JSONObject jsonObject){
//        String action = (String) jsonObject.get("action");
//        JSONObject resultJsonObject = new JSONObject();
//
//        if ("inquiryCreate".equals(action)) {
//            return inquiryController.createInquiry(jsonObject);
//        } else if ("inquiryReadRecentByWriter".equals(action)) {
//            return inquiryController.readRecentInquiryByWriter(jsonObject);
//        } else if ("inquiryReadAllByWriter".equals(action)) {
//            return inquiryController.readAllInquiryByWriter(jsonObject);
//        } else if ("inquiryReadAll".equals(action)) {
//            return inquiryController.readAllInquiry();
//        } else if ("inquiryUpdate".equals(action)) {
//            return inquiryController.updateInquiry(jsonObject);
//        } else if ("inquiryDelete".equals(action)) {
//            return inquiryController.deleteInquiry(jsonObject);
//        } else if ("salesPostCreate".equals(action)) {
//            return salesPostController.createSalesPost(jsonObject);
//        } else if ("salesPostReadRecentByWriter".equals(action)) {
//            return salesPostController.readRecentSalesPostByWriter(jsonObject);
//        } else if ("salesPostReadAllByWriter".equals(action)) {
//            return salesPostController.readAllSalesPostByWriter(jsonObject);
//        } else if ("salesPostReadAll".equals(action)) {
//            return salesPostController.readAllSalesPost();
//        } else if ("salesPostUpdate".equals(action)) {
//            return salesPostController.updateSalesPost(jsonObject);
//        } else if ("salesPostDelete".equals(action)) {
//            return salesPostController.deleteSalesPost(jsonObject);
//        } else if ("reviewCreate".equals(action)) {
//            return reviewController.createReview(jsonObject);
//        } else if ("reviewReadAllByWriter".equals(action)) {
//            return reviewController.readAllReviewByWriter(jsonObject);
//        } else if ("reviewReadAll".equals(action)) {
//            return reviewController.readAllReview();
//        } else if ("reviewUpdate".equals(action)) {
//            return reviewController.updateReview(jsonObject);
//        } else if ("reviewDelete".equals(action)) {
//            return reviewController.deleteReview(jsonObject);
//        } else if ("productCreate".equals(action)) {
//            return productController.createProduct(jsonObject);
//        } else if ("productReadOne".equals(action)) {
//            return productController.readProduct(jsonObject);
//        } else if ("productReadAll".equals(action)) {
//            return productController.readAllProduct();
//        } else if ("productUpdate".equals(action)) {
//            return productController.updateProduct(jsonObject);
//        } else if ("productDelete".equals(action)) {
//            return productController.deleteProduct(jsonObject);
//        } else {
//            resultJsonObject.put("result", "Error: Action is not valid.");
//        }
//        return resultJsonObject;
//    }
//}
