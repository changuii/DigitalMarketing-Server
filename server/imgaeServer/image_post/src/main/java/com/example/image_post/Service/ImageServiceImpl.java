package com.example.image_post.Service;

import com.example.image_post.Entity.ImageEntity;
import com.example.image_post.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    public ImageServiceImpl (@Autowired ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    @Override
    public ResponseEntity<String> upload(MultipartFile image) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImage(image.getBytes());
        imageEntity = imageRepository.save(imageEntity);
        return ResponseEntity.status(201).body(imageEntity.getImageNumber().toString());
    }

    @Override
    public ResponseEntity<byte[]> download(Long id) {
        return ResponseEntity.ok().body(imageRepository.getById(id).getImage());
    }
}
