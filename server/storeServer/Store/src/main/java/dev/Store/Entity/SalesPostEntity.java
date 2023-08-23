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
    @ElementCollection
    private List<Comment> comments;


    public SalesPostEntity() {
        this.postHitCount = 0L;
        this.postLike = 0L;
    }

    public void add(Comment comment){
        comments.add(comment);
    }

    public void remove(Comment comment){
        comments.remove(comment);
    }

    public Comment findCommentByCommentWriter(String commentWriter) {
        for (Comment comment : comments) {
            if (comment.getCommentWriter().equals(commentWriter)) {
                return comment;
            }
        }
        return null;
    }
}
