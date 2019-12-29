package com.nevzatcirak.example.controller;

import com.nevzatcirak.example.security.CustomRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResourceController {

    @Autowired
    CustomRestTemplate customRestTemplate;

    @GetMapping("/testinfo")
    public String getTestInfo() {
        String response = customRestTemplate.getForObject("http://localhost:8089/testinfo", String.class);
        return response;
    }

    @GetMapping("/testinfo2")
    public String getTestInfo2() {
        String response = customRestTemplate.getForObject("http://localhost:8095/testinfo", String.class);
        return response;
    }
}
