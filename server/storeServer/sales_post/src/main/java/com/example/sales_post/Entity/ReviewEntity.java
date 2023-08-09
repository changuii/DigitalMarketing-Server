package com.example.sales_post.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long reviewNumber;

    private String reviewAuthor;
    private String reviewContents;
    private int reviewStarRating;
    private int reviewLike;

    @ManyToOne
    @JoinColumn(name = "postNumber_id")
    private SalesPostEntity salesPostEntity;
}
