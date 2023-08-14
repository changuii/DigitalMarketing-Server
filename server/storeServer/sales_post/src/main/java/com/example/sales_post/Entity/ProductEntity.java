package com.example.sales_post.Entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import javax.validation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSerialNumber;

    private String productName;
    private Long productPrice;
    private Long productAmount;
    private Long productDeliveryFee;
    private String storeLocation;
    private String productPicture;
    private String productColor;
    private Long productSize;

    @ManyToOne() // FetchType.LAZY 설정을 권장
    @JoinColumn(name = "salesPostNumber", referencedColumnName = "postNumber")
    private SalesPostEntity salesPostEntity;

}