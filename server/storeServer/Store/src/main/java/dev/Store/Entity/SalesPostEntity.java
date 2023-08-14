package dev.Store.Entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SalesPost")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalesPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNumber;
    private String category;
    private String postTitle;
    private String postWriter;
    private String postContents;
    private String mainImage;
    @ElementCollection
    private List<String> descImages;
    private String storeLocation;
    private String postDate;
    @ColumnDefault("0")
    private Long postHitCount;
    @ColumnDefault("0")
    private Long postLike;

    @OneToMany(mappedBy = "salesPost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductEntity> products;

    public void addProduct(ProductEntity productEntity) {
        products.add(productEntity);
        productEntity.setSalesPostEntity(this);
    }

    public void removeProduct(ProductEntity productEntity) {
        products.remove(productEntity);
        productEntity.setSalesPostEntity(null);
    }
}
