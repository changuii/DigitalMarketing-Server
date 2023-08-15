package dev.Store.DTO;

import dev.Store.Entity.ImageData;
import dev.Store.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Component;

import javax.persistence.ElementCollection;
import java.util.List;

@Component
@Data
@AllArgsConstructor
public class SalesPostDTO {

    private Long salesPostNumber;
    private String category;
    private String postTitle;
    private String postWriter;
    private String postContents;
    private String mainImage;
    private String storeLocation;
    private String postDate;
    @ColumnDefault("0")
    private Long postHitCount;
    @ColumnDefault("0")
    private Long postLike;
    @ElementCollection
    private List<ImageData> descImages;
    @ElementCollection
    private List<Product> products;

    public SalesPostDTO() {
        this.postHitCount = 0L;
        this.postLike = 0L;
    }
}
