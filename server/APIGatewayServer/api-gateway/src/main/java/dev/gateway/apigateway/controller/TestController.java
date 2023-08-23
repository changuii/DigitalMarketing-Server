package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.ImageService;
import io.netty.handler.codec.base64.Base64Decoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    private final ImageService imageService;

    public TestController(
            @Autowired ImageService imageService
    ){
        this.imageService = imageService;
    }


    @PostMapping
    public ResponseEntity<List<String>> test(
            @RequestPart List<MultipartFile> img
            ) throws IOException {
        return ResponseEntity.status(201).body(this.imageService.saveImages(img));
    }

    @PostMapping("/file")
    public ResponseEntity<List<String>> testtest(
            @RequestBody JSONObject json
            ) throws IOException {

        return ResponseEntity.status(201).body(this.imageService.saveImages((List<MultipartFile>)json.get("data")));
    }

    @PostMapping("/test")
    public ResponseEntity<List<String>> multi(
            @RequestPart List<MultipartFile> img,
            @RequestPart MultipartFile mainImage,
            @RequestPart String postWriter,
            @RequestPart String postContents,
            @RequestPart String storeLocation,
            @RequestPart String postTitle,
            @RequestPart String category,
            @RequestPart String products

    ) throws IOException, ParseException {
//        products = products.substring(1, products.length()-1);
        logger.info(products);
        JSONParser parser = new JSONParser();
        JSONArray productJson = new JSONArray();
        productJson = (JSONArray) parser.parse(products);
        JSONObject json = new JSONObject();
        json.put("postWriter", postWriter);
        json.put("postContent", postContents);
        json.put("storeLocation", storeLocation);
        json.put("postTitle", postTitle);
        json.put("category", category);
        json.put("product", productJson);


        logger.info(json.toString());
        img.add(0, mainImage);

        return ResponseEntity.status(201).body(this.imageService.saveImages(img));
    }






}
