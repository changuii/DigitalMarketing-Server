package com.example.sales_post.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Inquiry")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryNumber;

    private String inquiryWriter;
    private String inquiryContents;

    @ManyToOne
    @JoinColumn(name = "salesPostNumber")
    private SalesPostEntity salesPostEntity;
}