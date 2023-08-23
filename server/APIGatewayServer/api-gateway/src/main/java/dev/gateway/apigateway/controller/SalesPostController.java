package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.SalesPostService;
import dev.gateway.apigateway.service.impl.SalesPostServiceImpl;
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
import java.util.List;

@RestController
@RequestMapping("/salespost")
public class SalesPostController {

    final static Logger logger = LoggerFactory.getLogger(SalesPostController.class);
    final SalesPostService salesPostService;

    public SalesPostController(
            @Autowired SalesPostService salesPostService
    ){
        this.salesPostService = salesPostService;
    }


    @PostMapping
    public ResponseEntity<JSONObject> createSalesPost(
            @RequestPart List<MultipartFile> img,
            @RequestPart MultipartFile mainImage,
            @RequestPart String postWriter,
            @RequestPart String postContents,
            @RequestPart String storeLocation,
            @RequestPart String postTitle,
            @RequestPart String category,
            @RequestPart String products
    ) throws IOException {
        img.add(0, mainImage);
        logger.info(products);
        JSONParser parser = new JSONParser();
        JSONArray productJson = new JSONArray();
        try {
            productJson = (JSONArray) parser.parse(products);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body((JSONObject) new JSONObject().put("result", "product가 잘못된 값입니다."));
        }
        JSONObject json = new JSONObject();
        json.put("postWriter", postWriter);
        json.put("postContents", postContents);
        json.put("storeLocation", storeLocation);
        json.put("postTitle", postTitle);
        json.put("category", category);
        json.put("products", productJson);

        return this.salesPostService.createSalesPost(json, img);
    }


    @GetMapping("/writer/recent")
    public ResponseEntity<JSONObject> readRecentByWriterSalesPost(
            @RequestBody JSONObject json
    ){

        return this.salesPostService.readRecentByWriterSalesPost(json);
    }

    @GetMapping("/writer/readAll")
    public ResponseEntity<JSONObject> readAllByWriterSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.readAllByWriterSalesPost(json);
    }

    @GetMapping("/{salesPostNumber}")
    public ResponseEntity<JSONObject> readByWriterTitleSalesPost(
            @PathVariable("salesPostNumber") long id
    ){
        return this.salesPostService.readBySalesPostNumber(id);
    }


    @GetMapping
    public ResponseEntity<JSONObject> readAllSalesPost(){
        return this.salesPostService.readAllSalesPost();
    }

    @PutMapping
    public ResponseEntity<JSONObject> updateSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.updateSalesPost(json);
    }

    @DeleteMapping
    public ResponseEntity<JSONObject> deleteSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.deleteSalesPost(json);
    }

    @PostMapping("/like")
    public ResponseEntity<JSONObject> postLikeSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.postLikeSalesPost(json);
    }

    @PostMapping("/hit")
    public ResponseEntity<JSONObject> hitCountSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.hitCountSalesPost(json);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<JSONObject> readAllByCategorySalesPost(
            @PathVariable("category") String category
    ){
        return this.salesPostService.readAllByCategorySalesPost(category);
    }

    @PostMapping("/comment")
    public ResponseEntity<JSONObject> createComment(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.createComment(json);

    }

    @DeleteMapping("/comment")
    public ResponseEntity<JSONObject> deleteComment(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.deleteComment(json);
    }

}
