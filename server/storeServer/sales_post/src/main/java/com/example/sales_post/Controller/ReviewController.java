package com.example.sales_post.Controller;

import com.example.sales_post.Service.ReviewService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    public ReviewController(@Autowired ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //CREATE
    @PostMapping("/create")
    public JSONObject postReview(@RequestBody JSONObject jsonObject){
        logger.info(jsonObject.toString());
        return reviewService.create(jsonObject);
    }

    //READ
    //작성자의 모든 리뷰 READ
    @GetMapping("/read-all-writer")
    public List<JSONObject> readAllReviewByWriter(@RequestBody JSONObject jsonObject){
        logger.info("read-By-Writer");
        return reviewService.readAllByWriter(jsonObject);
    }

    @GetMapping("/read-all")
    public List<JSONObject> readAllReview(){
        logger.info("read-All");
        return reviewService.readAll();
    }

    //UPDATE
    @PutMapping("/update")
    public JSONObject updateReview(@RequestBody JSONObject jsonObject){
        logger.info("update content" + jsonObject);
        return reviewService.update(jsonObject);
    }

    //DELETE
    @DeleteMapping("/delete")
    public JSONObject deleteReview(@RequestBody JSONObject jsonObject){
        logger.info("delete");
        return reviewService.delete(jsonObject);
    }

    //별점 내림차순, 오름차순 정렬기능 구현과 각 별점별로 리뷰 볼 수 있는 기능 추가하면 좋을 듯?
}
