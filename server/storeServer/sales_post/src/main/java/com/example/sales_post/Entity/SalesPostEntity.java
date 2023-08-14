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

    @OneToMany(mappedBy = "salesPostEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InquiryEntity> inquiries;

    @OneToMany(mappedBy = "salesPostEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "salesPostEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;

    public void addInquiry(InquiryEntity inquiryEntity) {
        inquiries.add(inquiryEntity);
        inquiryEntity.setSalesPostEntity(this);
    }

    public void addReview(ReviewEntity reviewEntity) {
        reviews.add(reviewEntity);
        reviewEntity.setSalesPostEntity(this);
    }

    public void addProduct(ProductEntity productEntity) {
        products.add(productEntity);
        productEntity.setSalesPostEntity(this);
    }

    public void removeInquiry(InquiryEntity inquiryEntity) {
        inquiries.remove(inquiryEntity);
        inquiryEntity.setSalesPostEntity(null);
    }

    public void removeReview(ReviewEntity reviewEntity) {
        reviews.remove(reviewEntity);
        reviewEntity.setSalesPostEntity(null);
    }

    public void removeProduct(ProductEntity productEntity) {
        products.remove(productEntity);
        productEntity.setSalesPostEntity(null);
    }
}