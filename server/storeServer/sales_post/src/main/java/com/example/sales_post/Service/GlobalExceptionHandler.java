//package com.example.sales_post.Service;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import javax.validation.*;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<String> handleValidationException(ValidationException ex) {
//        logger.info(ex.toString());
//        return ResponseEntity.badRequest().body("ㅁㄴㅇㅁㄴㅇㄹㅁㄴㅇㄹㅁ");
////        return ResponseEntity.badRequest().body("뭘바");
////        return ex.getMessage();
//    }
//}
