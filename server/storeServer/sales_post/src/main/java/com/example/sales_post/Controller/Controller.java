package com.example.sales_post.Controller;


import com.example.sales_post.Entity.SalesPostEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/test")
    public void post(@RequestBody SalesPostEntity a)
    {
        System.out.println(a);
    }



}
