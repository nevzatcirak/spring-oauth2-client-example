package com.nevzatcirak.examples.oauth2client.controller;

import com.nevzatcirak.examples.oauth2client.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nevzat ÇIRAK
 * @mail ncirak@havelsan.com.tr
 * Created by ncirak at 02/07/2020
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/test")
    public String getTestInfo() {
        return "OK. You have authenticated! :)";
    }

    @GetMapping("/rest/test")
    public String getRestTest(){
        return restTemplate.getForObject("http://localhost:8089/testinfo", String.class);
    }

    @GetMapping("/user/{username}")
    public String getUser(@PathVariable String username){
        return userDetailsService.loadUserByUsername(username).toString();
    }

}
