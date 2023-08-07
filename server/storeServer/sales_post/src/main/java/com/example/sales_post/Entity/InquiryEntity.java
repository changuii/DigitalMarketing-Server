package com.example.sales_post.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Inquiry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryEntity {

    @Id
    private Long inquiryNumber;

    private String inquiryAuthor;
    private String inquiryContents;
}
