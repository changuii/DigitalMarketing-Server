package com.example.sales_post.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    // product_info: 제품정보
    @Id
    private String productSerialnum;
    private String productName;
    private int productPrice;
    private int productAmount;
    private int productDeliveryFee;
    private String storeLocation;
}
