package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/img")
public class ImageController {
    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);

    public final ImageService imageService;

    public ImageController(
            @Autowired ImageService imageService
    ){
        this.imageService = imageService;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> downloadImage(
            @PathVariable("id") long id
    ){
        return ResponseEntity.ok().body(this.imageService.downloadImage(id));
    }


}
