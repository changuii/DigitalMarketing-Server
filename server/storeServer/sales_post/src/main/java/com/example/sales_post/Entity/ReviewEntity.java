package com.example.sales_post.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "작성자는 필수 입력값 입니다. ㅅㅂ")
    private String reviewWriter; //작성자
    private String reviewContents; //내용
    private Integer reviewStarRating; //별점
    private Integer reviewLike; //좋아요
    private String reviewDate; //작성날짜

    @ManyToOne() // FetchType.LAZY 설정을 권장
    @JoinColumn(name = "salesPostNumber", referencedColumnName = "postNumber")
    private SalesPostEntity salesPostEntity; //리뷰의 제품 게시글
}
