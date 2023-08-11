package dev.gateway.apigateway.controller;


import dev.gateway.apigateway.service.SalesPostService;
import dev.gateway.apigateway.service.impl.SalesPostServiceImpl;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salespost")
public class SalesPostController {

    final static Logger logger = LoggerFactory.getLogger(SalesPostController.class);
    final SalesPostService salesPostService;

    public SalesPostController(
            @Autowired SalesPostService salesPostService
    ){
        this.salesPostService = salesPostService;
    }


    @PostMapping
    public ResponseEntity<JSONObject> createSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.createSalesPost(json);
    }

    @GetMapping("/recent")
    public ResponseEntity<JSONObject> readRecentByWriterSalesPost(
            @RequestBody JSONObject json
    ){

        return this.salesPostService.readRecentByWriterSalesPost(json);
    }

    @GetMapping("/writer")
    public ResponseEntity<JSONObject> readAllByWriterSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.readAllByWriterSalesPost(json);
    }


    @GetMapping
    public ResponseEntity<JSONObject> readAllSalesPost(){
        return this.salesPostService.readAllSalesPost();
    }

    @PutMapping
    public ResponseEntity<JSONObject> updateSalesPost(
            @RequestBody JSONObject json
    ){
        return this.salesPostService.updateSalesPost(json);
    }

    @DeleteMapping("{salesPostNum}")
    public ResponseEntity<JSONObject> deleteSalesPost(
            @PathVariable long salesPostNum
    ){
        return this.salesPostService.deleteSalesPost(salesPostNum);
    }


}
