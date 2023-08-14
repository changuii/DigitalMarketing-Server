package dev.Store.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductId implements Serializable {
    @Column(name = "number")
    private Long number;

    @Column(name = "postNumber")
    private Long postNumber;
}