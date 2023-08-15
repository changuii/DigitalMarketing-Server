package dev.Store.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SalesPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public SalesPostEntity() {
        this.postHitCount = 0L;
        this.postLike = 0L;
    }
}
