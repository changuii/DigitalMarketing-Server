package com.example.sales_post.Entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "salesPostNumber")
    private SalesPostEntity salesPostEntity;
}