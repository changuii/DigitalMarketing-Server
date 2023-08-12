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
    public JSONObject postProduct(@RequestBody JSONObject jsonObject){ return productServiceImpl.create(jsonObject);}

    //PUT
    @PutMapping("/update")
    public JSONObject putProduct(@RequestBody JSONObject jsonObject){return productServiceImpl.update(jsonObject);}

    //DELETE
    @DeleteMapping("/delete")
    public JSONObject deleteProduct(@RequestBody JSONObject jsonObject){return productServiceImpl.delete(jsonObject);}
}
