package com.example.sales_post.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Inquiry")
@Getter
@Setter
@NoArgsConstructor
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryNumber;

    private String inquiryAuthor;
    private String inquiryContents;

//    @ManyToOne
//    @JoinColumn(name = "postNumber_id")
//    private SalesPostEntity salesPostEntity;

//    @Builder
//    public InquiryEntity(Long inquiryNumber, String inquiryAuthor, String inquiryContents, SalesPostEntity salesPostEntity) {
//        this.inquiryNumber = inquiryNumber;
//        this.inquiryAuthor = inquiryAuthor;
//        this.inquiryContents = inquiryContents;
//        this.salesPostEntity = salesPostEntity;
//    }

    @Builder
    public InquiryEntity(Long inquiryNumber, String inquiryAuthor, String inquiryContents) {
        this.inquiryNumber = inquiryNumber;
        this.inquiryAuthor = inquiryAuthor;
        this.inquiryContents = inquiryContents;
    }
}
