package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.ProductService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(
            @Autowired ProductService productService
    ){
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<JSONObject> createProduct(
            @RequestBody JSONObject json
    ){
        return this.productService.createProduct(json);
    }

    @GetMapping("/one")
    public ResponseEntity<JSONObject> readProduct(
            @RequestBody JSONObject json
    ){
        return this.productService.readProduct(json);
    }


    @GetMapping
    public ResponseEntity<JSONObject> readAllProduct(){
        return this.productService.readAllProduct();
    }

    @PutMapping
    public ResponseEntity<JSONObject> updateProduct(
            @RequestBody JSONObject json
    ){
        return this.productService.updateProduct(json);
    }

    @DeleteMapping
    public ResponseEntity<JSONObject> deleteProduct(
            @RequestBody JSONObject json
    ){
        return this.productService.deleteProduct(json);
    }
}
