package dev.gateway.apigateway.service.impl;

import dev.gateway.apigateway.service.ImageService;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.util.List;


@Service
public class ImageServiceImpl implements ImageService {


    @Override
    public String saveImage(MultipartFile img) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // MultipartFile을 byte[] 형태로 변환합니다.
        byte[] data = img.getBytes();

        // ByteArrayResouce를 이용하여 바디를 설정합니다.
        ByteArrayResource body = new ByteArrayResource(data) {
            @Override
            public String getFilename() {
                return img.getOriginalFilename();
            }
        };

        // multipart request를 생성합니다.
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("image", body);

        // header를 설정합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        // API 호출
        String url = "http://rhljh201.codns.com/image/upload";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return responseEntity.getBody();
    }

    @Override
    public List<String> saveImages(List<MultipartFile> images) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();

        // MultipartFile을 byte[] 형태로 변환합니다.
        for(MultipartFile img : images) {
            byte[] data = img.getBytes();
            ByteArrayResource body = new ByteArrayResource(data) {
                @Override
                public String getFilename() {
                    return img.getOriginalFilename();
                }
            };
            bodyMap.add("image", body);
        }



        // header를 설정합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        // API 호출
        String url = "http://rhljh201.codns.com/image/multiple-upload";
        ResponseEntity<List> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, List.class);

        List asd = responseEntity.getBody();
        System.out.println(asd.toString());
        return responseEntity.getBody();
    }

    @Override
    public byte[] downloadImage(long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();


        String url = "http://121.55.117.123/image/download/"+id;
        byte[] img = restTemplate.getForObject(url, byte[].class);


        return img;
    }

}
