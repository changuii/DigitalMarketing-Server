package dev.gateway.apigateway.service.impl;


import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {


    // topic name
    private final KafkaTemplate<String, JSONObject> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    public KafkaService(
            @Autowired KafkaTemplate kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(JSONObject json, String TOPIC) {
        logger.info("Producer Message : {}", json.toString());
        // DTO를 JSON으로 맵핑
        this.kafkaTemplate.send(TOPIC, json);
//        for (int i = 0; i < 10; i++) {
//            this.kafkaTemplate.send(TOPIC, json);
//        }
    }
}
