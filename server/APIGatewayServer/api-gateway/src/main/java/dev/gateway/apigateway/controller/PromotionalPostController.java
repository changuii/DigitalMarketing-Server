package dev.gateway.apigateway.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import dev.gateway.apigateway.service.PromotionalPostService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            @RequestPart List<MultipartFile> img,
            @RequestPart MultipartFile mainImage,
            @RequestPart String salesPostNumber,
            @RequestPart String pmCategory,
            @RequestPart String pmPostTitle,
            @RequestPart String pmPostWriter,
            @RequestPart String pmTag,
            @RequestPart String pmPostContents
    ) throws IOException {
        img.add(0, mainImage);

        List<String> tag = new ArrayList<>();
        String target[] = pmTag.split(",");
        for(String str : target){
            tag.add(str);
        }

        JSONObject json = new JSONObject();
        json.put("pmCategory", pmCategory);
        json.put("salesPostNumber", salesPostNumber);
        json.put("pmPostTitle", pmPostTitle);
        json.put("pmPostWriter", pmPostWriter);
        json.put("pmPostContents", pmPostContents);
        json.put("pmTag", tag);


        return this.promotionalPostService.createPromotionalPost(json, img);
    }

    @GetMapping("/one")
    public ResponseEntity<JSONObject> readPromotionalPost(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.readPromotionalPost(json);
    }

    @GetMapping
    public ResponseEntity<JSONObject> readAllPromotionalPost() throws JsonProcessingException {
        return this.promotionalPostService.readAllPromotionalPost();
    }

    @GetMapping("/category")
    public ResponseEntity<JSONObject> categoryReadAllPromotionalPost(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.categoryReadAllPromotionalPost(json);
    }

    @GetMapping("/tag")
    public ResponseEntity<JSONObject> tagReadAllPromotionalPost(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.tagReadAllPromotionalPost(json);
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


    @PostMapping("/comment")
    public ResponseEntity<JSONObject> createComment(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.createComment(json);
    }

    @PutMapping("/comment")
    public ResponseEntity<JSONObject> updateComment(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.updateComment(json);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<JSONObject> deleteComment(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.deleteComment(json);
    }

    @PostMapping("/like")
    public ResponseEntity<JSONObject> postLike(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.postLike(json);
    }

    @PostMapping("/comment/like")
    public ResponseEntity<JSONObject> commentLike(
            @RequestBody JSONObject json
    ){
        return this.promotionalPostService.commentLike(json);
    }


}
