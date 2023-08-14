package dev.Store.Controller;

import dev.Store.Service.SalesPostService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class SalesPostController {
    private SalesPostService salesPostService;

    public SalesPostController(@Autowired SalesPostService salesPostService) {
        this.salesPostService = salesPostService;
    }
    @PostMapping
    public JSONObject create(@RequestBody JSONObject jsonObject){return salesPostService.create(jsonObject);}
    @GetMapping("read")
    public JSONObject read(@RequestBody JSONObject jsonObject){return salesPostService.read(jsonObject);}
    @GetMapping("all")
    public JSONObject readAll(){
        return salesPostService.readAll();
    }
    @PutMapping
    public JSONObject update(@RequestBody JSONObject jsonObject){return salesPostService.update(jsonObject);}
    @DeleteMapping
    public JSONObject delete(@RequestBody JSONObject jsonObject){return salesPostService.delete(jsonObject);}
}