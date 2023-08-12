package com.example.sales_post.Controller;


import com.example.sales_post.Service.ProductServiceImpl;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    //DI
    private final ProductServiceImpl productServiceImpl;
    public ProductController(@Autowired ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }


    //GET
    @GetMapping("/get")
    public JSONObject getProduct(@RequestBody JSONObject jsonObject) {return productServiceImpl.read(jsonObject);}

    @GetMapping("/get-all")
    public List<JSONObject> getAllProduct(){return productServiceImpl.readAll();}

    //POST
    @PostMapping("/create")
    public JSONObject postProduct(@Validated @RequestBody JSONObject jsonObject, BindingResult bindingResult){
        if(bindingResult.hasErrors())
        {// BadRequest에 대한 오류 메세지 출력임 근데 동작 안됨
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = new StringBuilder("validation error")
                    .append("field: ").append(fieldError.getField())
                    .append(", code: ").append(fieldError.getCode())
                    .append(", message: ").append(fieldError.getDefaultMessage())
                    .toString();

            System.out.println(errorMessage);
            return productServiceImpl.resultJsonObject("fail");
        }
        return productServiceImpl.create(jsonObject);}

    //PUT
    @PutMapping("/update")
    public JSONObject putProduct(@RequestBody JSONObject jsonObject){return productServiceImpl.update(jsonObject);}

    //DELETE
    @DeleteMapping("/delete")
    public JSONObject deleteProduct(@RequestBody JSONObject jsonObject){return productServiceImpl.delete(jsonObject);}
}
