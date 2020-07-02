package com.nevzatcirak.examples.oauth2client.controller;

import com.nevzatcirak.examples.oauth2client.security.CustomRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nevzat ÇIRAK
 * @mail ncirak@havelsan.com.tr
 * Created by ncirak at 02/07/2020
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String getTestInfo() {
        return "OK. You have authenticated! :)";
    }

    @GetMapping("/auth/test")
    public String getAuthTestInfo() {
        return "OK. You have anonymous authenticated! :)";
    }
}
