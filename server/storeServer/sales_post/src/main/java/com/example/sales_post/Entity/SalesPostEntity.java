package com.example.sales_post.Entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
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
    @NotNull(message = "NULL값이 들어왔다")
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