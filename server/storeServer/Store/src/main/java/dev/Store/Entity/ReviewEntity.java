//package dev.Store.Entity;
//
//
//import lombok.*;
//import javax.persistence.*;
//
//@Embeddable
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ReviewEntity {
//    private Long reviewNumber;
//    private String reviewWriter;
//    private String reviewContents;
//    private Integer reviewStarRating;
//    private Integer reviewLike;
//    private String reviewDate;
//
//    @ManyToOne() // FetchType.LAZY 설정을 권장
//    @JoinColumn(name = "salesPostNumber", referencedColumnName = "postNumber")
//    private SalesPostEntity salesPostEntity;
//}
