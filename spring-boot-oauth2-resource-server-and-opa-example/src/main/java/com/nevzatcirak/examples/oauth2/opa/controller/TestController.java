package com.nevzatcirak.examples.oauth2.opa.controller;

import com.nevzatcirak.examples.oauth2.opa.security.CustomUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("/rest")
public class TestController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /*@EventListener(ApplicationReadyEvent.class)
    private void init(){
        logger.info(restTemplate.getForObject("http://localhost:8089/testinfo", String.class));
    }*/

    @GetMapping("/test")
    public String getTestInfo() {
        return "OK. You have authenticated! :)";
    }

    @GetMapping("/permit-all")
    public String getTestPermitAllInfo() {
        return "OK. Permit All Endpoint! :)";
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
