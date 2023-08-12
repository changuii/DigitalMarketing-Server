package com.example.sales_post.Entity;

import lombok.*;
import javax.persistence.*;



@Entity
@Table(name = "SalesPost")
@Getter
@Setter
@NoArgsConstructor
public class SalesPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postNumber;

    private String category;
    private String postTitle;
    private String postWriter;
    private String postDate;
    private String postContents;
    private String postPicture;
    private long postHitCount;
    private long postLike;
    private String storeLocation;

    @Builder
    public SalesPostEntity(long postNumber, String category, String postTitle, String postWriter, String postDate, String postContents, String postPicture, long postHitCount, long postLike, String storeLocation) {
        this.postNumber = postNumber;
        this.category = category;
        this.postTitle = postTitle;
        this.postWriter = postWriter;
        this.postDate = postDate;
        this.postContents = postContents;
        this.postPicture = postPicture;
        this.postHitCount = postHitCount;
        this.postLike = postLike;
        this.storeLocation = storeLocation;
    }
}