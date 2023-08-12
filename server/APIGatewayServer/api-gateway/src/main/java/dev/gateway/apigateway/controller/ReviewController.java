package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.ReviewService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final static Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    public ReviewController(
            @Autowired ReviewService reviewService
    ){
        this.reviewService = reviewService;
    }


    @PostMapping
    public ResponseEntity<JSONObject> createReview(
            @RequestBody JSONObject json
    ){
        return this.reviewService.createReview(json);

    }

    @GetMapping("/writer")
    public ResponseEntity<JSONObject> readAllByWriterReview(
            @RequestBody JSONObject json
    ){
        return this.reviewService.readAllByWriterReview(json);
    }

    @GetMapping
    public ResponseEntity<JSONObject> readAllReview(){
        return this.reviewService.readAllReview();

    }

    @PutMapping
    public ResponseEntity<JSONObject> updateReview(
            @RequestBody JSONObject json
    ){
        return this.reviewService.updateReview(json);
    }

    @DeleteMapping
    public ResponseEntity<JSONObject> deleteReview(
            @RequestBody JSONObject json
    ){
        return reviewService.deleteReview(json);
    }








}
