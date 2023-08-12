package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.PromotionalPostService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotionalpost")
public class PromotionalPostController {
    private final static Logger logger = LoggerFactory.getLogger(PromotionalPostController.class);

    private final PromotionalPostService promotionalPostService;

    public PromotionalPostController(
            @Autowired PromotionalPostService promotionalPostService
    ){
        this.promotionalPostService = promotionalPostService;
    }



    @PostMapping
    public ResponseEntity<JSONObject> createPromotionalPost(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.createPromotionalPost(json);
    }

    @GetMapping
    public ResponseEntity<JSONObject> readAllPromotionalPost(){
        return this.promotionalPostService.readAllPromotionalPost();
    }

    @PutMapping
    public ResponseEntity<JSONObject> updatePromotionalPost(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.updatePromotionalPost(json);
    }

    @DeleteMapping
    public ResponseEntity<JSONObject> deletePromotionalPost(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.deletePromotionalPost(json);
    }





}
