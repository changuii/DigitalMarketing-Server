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
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long number;

    @JsonIgnore
    @EmbeddedId
    private ProductId id;

    private String detail;
    private Long quantity;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "salesPostNumber", referencedColumnName = "postNumber")
    @JsonIgnore
    private SalesPostEntity salesPostEntity;

    public void setId(Long number, Long postNumber){
        if (id == null) {
            id = new ProductId();
        }
        id.setNumber(number);
        id.setPostNumber(postNumber);
    }

    @JsonIgnore
    public Long getNumberById(){
        return id.getNumber();
    }
}
