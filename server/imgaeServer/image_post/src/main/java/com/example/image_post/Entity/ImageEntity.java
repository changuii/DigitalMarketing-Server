package com.example.image_post.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "image")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageNumber")
    private Long imageNumber; // 이미지번호

    @Column(name = "image")
    @Lob
    private byte[] image;



}
