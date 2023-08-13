package com.example.sales_post.Entity;


import lombok.*;
import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSerialNumber;    //  productSerialNumbe  : 제품 번호

    @Size(min = 1, max = 3, message = "상품 명은 최소 1글자 이상이어야 합니다.")
    private String productName;          //  productName         : 제품 이름
    @Max(10)
    private Long productPrice;           //  productPrice        : 제품 가격
    @NotNull(message = "널값 넣지마라 십새야")
    private Long productAmount;          //  productAmount       : 제품 수량
    private Long productDeliveryFee;     //  productDeliveryFee  : 제품 배달비
    private String storeLocation;        //  storeLocation       : 가게 위치

}