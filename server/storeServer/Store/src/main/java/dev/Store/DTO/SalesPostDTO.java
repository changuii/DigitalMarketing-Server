package dev.Store.DTO;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class SalesPostDTO {
    private Long postNumber;
    private String category;
    private String postTitle;
    private String postWriter;
    private String postContents;
    private String mainImage;
    private List<String> descImages;
    private String storeLocation;
    private String postDate;
    private Long postHitCount;
    private Long postLike;
}
