package com.example.sales_post.Service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
        return ex.getMessage();
    }


}
