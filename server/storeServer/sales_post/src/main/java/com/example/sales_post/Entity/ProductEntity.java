package com.example.sales_post.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSerialNumber;

    private String productName;
    private int productPrice;
    private int productAmount;
    private int productDeliveryFee;
    private String storeLocation;

    @Builder
    public ProductEntity(Long productSerialNumber, String productName, int productPrice,
                         int productAmount, int productDeliveryFee, String storeLocation)
    {
        this.productSerialNumber  =   productSerialNumber ;
        this.productName          =   productName         ;
        this.productPrice         =   productPrice        ;
        this.productAmount        =   productAmount       ;
        this.productDeliveryFee   =   productDeliveryFee  ;
        this.storeLocation        =   storeLocation       ;
    }
}