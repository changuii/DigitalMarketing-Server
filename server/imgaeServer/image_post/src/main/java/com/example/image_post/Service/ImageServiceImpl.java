package com.example.image_post.Service;

import com.example.image_post.Entity.ImageEntity;
import com.example.image_post.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final String hostname = "http://172.20.10.4:8080/image/download/";

    public ImageServiceImpl (@Autowired ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }



    @Override
    public ResponseEntity<String> upload(byte[] image) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImage(image);
        imageEntity = imageRepository.save(imageEntity);
        return ResponseEntity.status(201).body(this.hostname+imageEntity.getImageNumber().toString());
    }

    @Override
    public ResponseEntity<byte[]> download(Long id) {
        return ResponseEntity.ok().body(imageRepository.getById(id).getImage());
    }
}
