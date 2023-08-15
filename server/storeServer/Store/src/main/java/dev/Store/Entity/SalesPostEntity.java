package dev.Store.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<ImageData> descImages;
    private String storeLocation;
    private String postDate;
    @ColumnDefault("0")
    private Long postHitCount;
    @ColumnDefault("0")
    private Long postLike;
    @ElementCollection
    private List<Product> products;
}
