package com.example.sales_post.Entity;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String inquiryWriter;
    private String inquiryContents;

    @ManyToOne
    @JoinColumn(name = "postNumber")
    private SalesPostEntity salesPostEntity;

    @Builder
    public InquiryEntity(Long inquiryNumber, String inquiryWriter, String inquiryContents, SalesPostEntity salesPostEntity) {
        this.inquiryNumber = inquiryNumber;
        this.inquiryWriter = inquiryWriter;
        this.inquiryContents = inquiryContents;
        this.salesPostEntity = salesPostEntity;
    }
}
