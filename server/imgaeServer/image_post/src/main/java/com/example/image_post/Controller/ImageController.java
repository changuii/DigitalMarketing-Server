package com.example.image_post.Controller;


import com.example.image_post.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(@Autowired ImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestPart MultipartFile image){
        try {
            return this.imageService.upload(image.getBytes());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("fail");
        }
    }
    @PostMapping("/multiple-upload")
    public ResponseEntity<List<String>> multipleupload(
            @RequestPart List<MultipartFile> image){
        try {
            List<String> url = new ArrayList<>();
            for (MultipartFile multipartFile : image) {
                url.add(this.imageService.upload(multipartFile.getBytes()).getBody());
            }
            return ResponseEntity.status(201).body(url);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @PostMapping("/upload/test")
    public ResponseEntity<String> uploadtest(@RequestPart List<MultipartFile> files) throws IOException {
        return this.imageService.upload(files.get(0).getBytes());
    }


    @GetMapping(value ="/download/{id}",produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> download(@PathVariable("id") Long id){

        return this.imageService.download(id);
    }
}
