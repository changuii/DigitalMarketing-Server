package com.example.image_post.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public ResponseEntity<String> upload(byte[] image) throws IOException;
    public ResponseEntity<byte[]> download(Long id);
}
