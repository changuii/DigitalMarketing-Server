package dev.gateway.apigateway.service;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface ProductService {

    public ResponseEntity<JSONObject> createProduct(JSONObject json);
    public ResponseEntity<JSONObject> readProduct(JSONObject json);
    public ResponseEntity<JSONObject> readAllProduct();
    public ResponseEntity<JSONObject> updateProduct(JSONObject json);
    public ResponseEntity<JSONObject> deleteProduct(JSONObject json);
    public void print();
}
