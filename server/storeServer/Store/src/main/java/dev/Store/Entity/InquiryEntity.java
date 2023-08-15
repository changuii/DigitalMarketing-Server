//package dev.Store.Entity;
//
//import lombok.*;
//import javax.persistence.*;
//
//@Entity
//@Table(name = "Inquiry")
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//public class InquiryEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long inquiryNumber;
//    private String inquiryWriter;
//    private String inquiryContents;
//    private String inquiryDate;
//
//    @ManyToOne() // FetchType.LAZY 설정을 권장
//    @JoinColumn(name = "salesPostNumber", referencedColumnName = "postNumber")
//    private SalesPostEntity salesPostEntity;
//}