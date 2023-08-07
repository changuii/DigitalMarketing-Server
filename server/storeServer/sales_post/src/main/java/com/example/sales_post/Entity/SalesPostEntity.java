package com.example.sales_post.Entity;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


//==========================================================================================
// 추가 사항: 어노테이션 설정 할 것,
//==========================================================================================



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesPostEntity extends ProductEntity {

    // salesPost: 게시글
    @Id
    @NotNull
    private long postNumber;

    @Column
    private String category;

    @Column
    private String postTitle;

    @Column
    private String postAuthor;

    @Column
    private String postDate;

    @Column
    private String postContents;

    @Column
    private String postPicture;

    @Column
    private long postHitCount;

    @Column
    private long postLike;

    @Column
    private String storeLocation;

}
