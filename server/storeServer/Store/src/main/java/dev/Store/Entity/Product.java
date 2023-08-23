package dev.Store.Entity;

import lombok.*;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long number;
    private Long price;
    private Long quantity;
    private String detail;
}
