package com.example.sales_post.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNumber; //리뷰번호 Auto Increment

    private String reviewWriter; //작성자
    private String reviewContents; //내용
    private Integer reviewStarRating; //별점
    private Integer reviewLike; //좋아요
    private String reviewDate; //작성날짜

    @ManyToOne
    @JoinColumn(name = "salesPostNumber") //외래키 생성, SalePostRepository 의 id와 매핑
    private SalesPostEntity salesPostEntity; //리뷰의 제품 게시글
}
