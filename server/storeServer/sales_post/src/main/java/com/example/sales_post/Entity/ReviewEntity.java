package com.example.sales_post.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Review")
@Getter
@Setter
@NoArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNumber;

    private String reviewWriter;
    private String reviewContents;
    private int reviewStarRating;
    private int reviewLike;

    @ManyToOne
    @JoinColumn(name = "postNumber")
    private SalesPostEntity salesPostEntity;

    @Builder
    public ReviewEntity(Long reviewNumber, String reviewWriter, String reviewContents, int reviewStarRating, int reviewLike, SalesPostEntity salesPostEntity) {
        this.reviewNumber = reviewNumber;
        this.reviewWriter = reviewWriter;
        this.reviewContents = reviewContents;
        this.reviewStarRating = reviewStarRating;
        this.reviewLike = reviewLike;
        this.salesPostEntity = salesPostEntity;
    }
}
