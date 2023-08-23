package dev.gateway.apigateway.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    public String saveImage(MultipartFile image) throws IOException;
    public List<String> saveImages(List<MultipartFile> images) throws IOException;
    public byte[] downloadImage(long id);

}
