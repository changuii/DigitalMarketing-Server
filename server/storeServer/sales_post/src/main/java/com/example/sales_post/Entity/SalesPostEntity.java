package com.example.sales_post.Entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "SalesPost")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNumber;

    private String category;
    private String postTitle;
    private String postWriter;
    private String postDate;
    private String postContents;
    private String postPicture;
    private Long postHitCount;
    private Long postLike;
    private String storeLocation;

//    @OneToMany
//    @JoinColumn(name = "productSerialNumber")
//    private List<ProductEntity> productEntityList;
}