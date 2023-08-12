package com.example.sales_post.Entity;


import lombok.*;
import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSerialNumber;    //  productSerialNumbe  : 제품 번호

    @NotEmpty
    private String productName;          //  productName         : 제품 이름
    @NotEmpty
    private Long productPrice;           //  productPrice        : 제품 가격

    private Long productAmount;          //  productAmount       : 제품 수량
    private Long productDeliveryFee;     //  productDeliveryFee  : 제품 배달비
    private String storeLocation;        //  storeLocation       : 가게 위치

}