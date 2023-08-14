package com.example.sales_post.Controller;
import com.example.sales_post.Service.ProductService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService productService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(@Autowired ProductService productService,
                             @Autowired RedisTemplate redisTemplate) {
        this.productService = productService;
        this.redisTemplate = redisTemplate;
    }

    public JSONObject readProduct(JSONObject jsonObject) {
        return productService.read(jsonObject);
    }

    public JSONObject readAllProduct() {
        return productService.readAll();
    }

    public JSONObject createProduct(JSONObject jsonObject) {return productService.create(jsonObject);}

    public JSONObject updateProduct(JSONObject jsonObject){return productService.update(jsonObject);}

    public JSONObject deleteProduct(JSONObject jsonObject){return productService.delete(jsonObject);}

//    @KafkaListener(topics = "ProductRequest", groupId = "foo")
//    public void getMessage(JSONObject jsonObject){
//        logger.info("KafkaMessage: " + jsonObject.toString());
//        actionControl(jsonObject);
//    }
//
//    public void sendMessage(String requestId, JSONObject jsonObject){
//        HashOperations<String, String, JSONObject> hashOperations = redisTemplate.opsForHash();
//        hashOperations.put("ProductResponse", requestId, jsonObject);
//    }
//
//    public void actionControl(JSONObject jsonObject){
//        String action = (String) jsonObject.get("action");
//        String requestId = (String) jsonObject.get("requestId");
//
//        if ("productCreate".equals(action)) {
//            JSONObject resultJsonObject = createProduct(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("productReadOne".equals(action)) {
//            JSONObject resultJsonObject = readProduct(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("productReadAll".equals(action)) {
//            JSONObject resultJsonObject = readAllProduct();
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("productUpdate".equals(action)) {
//            JSONObject resultJsonObject = updateProduct(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//        if ("productDelete".equals(action)) {
//            JSONObject resultJsonObject = deleteProduct(jsonObject);
//            sendMessage(requestId, resultJsonObject);
//        }
//    }
}
