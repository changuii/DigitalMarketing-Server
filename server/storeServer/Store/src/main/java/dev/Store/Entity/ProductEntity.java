package dev.Store.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    private String detail;
    private Long quantity;
    private Long price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "salesPostNumber", referencedColumnName = "postNumber")
    private SalesPostEntity salesPostEntity;
}
