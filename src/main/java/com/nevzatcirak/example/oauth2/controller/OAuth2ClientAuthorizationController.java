package com.nevzatcirak.example.oauth2.controller;

import com.nevzatcirak.example.oauth2.support.HttpAPIResourceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
@Controller
public class OAuth2ClientAuthorizationController extends HttpAPIResourceSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2ClientAuthorizationController.class);

    /**
     * 通过OAuth2 - authorization_code模式的redirect_uri
     * 一般情况下，该redirect_uri并不会进入，会被savedRequest取代
     * @param registrationId
     * @return
     */
    @GetMapping("/authorize/oauth2/code/{registrationId}")
    public String authorized(String registrationId) {
        LOGGER.info(">>> authorized, registrationId = {}", registrationId);
        //重定向去首页，手动点击<a>Authorization Code</a>将会获取到数据
        return "redirect:/index";
    }

}
