package dev.Store.Entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String commentWriter;
    private String commentContents;
    private String commentDate;

    public Comment(String commentWriter, String commentContents) {
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
    }
}
