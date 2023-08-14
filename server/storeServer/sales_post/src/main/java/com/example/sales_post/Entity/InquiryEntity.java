package com.example.sales_post.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "Error: 작성자는 필수 입력 값 입니다.")
    private String inquiryWriter;
    private String inquiryContents;
    private String inquiryDate;

    @ManyToOne() // FetchType.LAZY 설정을 권장
    @JoinColumn(name = "salesPostNumber", referencedColumnName = "postNumber")
    private SalesPostEntity salesPostEntity;
}