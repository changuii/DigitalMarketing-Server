package dev.Store.Entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageData {
    private String image;
}
