package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.dto.TestDTO;
import dev.gateway.apigateway.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



// 카프카 테스트용
@RestController
@RequestMapping("/test")
public class TestController {

    private final KafkaService kafkaService;

    public TestController(
            @Autowired KafkaService kafkaService
    ){
        this.kafkaService = kafkaService;
    }

    @PostMapping()
    public void testText(
            @RequestBody TestDTO testDTO
            ){
        this.kafkaService.sendMessage(testDTO);
    }


}
