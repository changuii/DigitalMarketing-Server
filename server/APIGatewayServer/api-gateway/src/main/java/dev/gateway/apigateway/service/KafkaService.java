package dev.gateway.apigateway.service;


import dev.gateway.apigateway.dto.TestDTO;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {


    // topic name
    private static final String TOPIC = "test1";
    private final KafkaTemplate<String, JSONObject> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    public KafkaService(
            @Autowired KafkaTemplate kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TestDTO message) {
        logger.info("Producer Message : {}", message);
        JSONObject json = new JSONObject();
        json.put("title", message.getTitle());
        json.put("text", message.getText());

        this.kafkaTemplate.send(TOPIC, json);
//        for (int i = 0; i < 10; i++) {
//            this.kafkaTemplate.send(TOPIC, json);
//        }
    }
}
