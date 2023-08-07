package com.example.sales_post.Service;


import com.example.sales_post.Entity.SalesPostEntity;
import com.example.sales_post.Repository.SalesPostRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class SalesPostServiceImpl implements SalesPostService{

    private SalesPostRepository salespostRepository;

    public SalesPostServiceImpl(@Autowired SalesPostRepository salespostRepository) {
        this.salespostRepository = salespostRepository;
    }

    public SalesPostEntity createPost(JSONObject jsonObject) {
        SalesPostEntity post = toEntity(jsonObject);
        return salespostRepository.save(post);
    }


    public Optional<SalesPostEntity> readPost(Long postNumber) {
        Optional<SalesPostEntity> post= salespostRepository.findById(postNumber);
        if(post.isPresent()){
            return post.orElseGet(Optional.empty());
        }


        return ;
    }


    public SalesPostEntity updatePost(Long postNumber, JSONObject jsonObject) {
        Optional<SalesPostEntity> postOptional = salespostRepository.findById(postNumber);
        if (postOptional.isPresent()) {
            SalesPostEntity post = postOptional.get();
            post.setCategory(jsonObject.getString("category"));
            post.setPostTitle(jsonObject.getString("postTitle"));
            post.setPostAuthor(jsonObject.getString("postAuthor"));
            post.setPostDate(LocalDate.parse(jsonObject.getString("postDate")));
            post.setPostContents(jsonObject.getString("postContents"));
            post.setPostPicture(jsonObject.getString("postPicture"));
            post.setPostHitCount(jsonObject.getInt("postHitCount"));
            post.setPostLike(jsonObject.getInt("postLike"));
            post.setStoreLocation(jsonObject.getString("storeLocation"));
            return salespostRepository.save(post);
        }
        return null;
    }


    public void deletePost(Long postNumber) {
        salespostRepository.deleteById(postNumber);
    }


    private SalesPostEntity toEntity(JSONObject jsonObject) {
        String category = jsonObject.getString("category");
        String postTitle = jsonObject.getString("postTitle");
        String postAuthor = jsonObject.getString("postAuthor");
        LocalDate postDate = LocalDate.parse(jsonObject.getString("postDate"));
        String postContents = jsonObject.getString("postContents");
        String postPicture = jsonObject.getString("postPicture");
        int postHitCount = jsonObject.getInt("postHitCount");
        int postLike = jsonObject.getInt("postLike");
        String storeLocation = jsonObject.getString("storeLocation");

        return new Post(category, postTitle, postAuthor, postDate, postContents, postPicture, postHitCount, postLike, storeLocation);
    }
}
