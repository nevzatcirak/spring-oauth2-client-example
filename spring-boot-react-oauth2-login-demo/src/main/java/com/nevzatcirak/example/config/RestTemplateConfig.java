package com.nevzatcirak.example.config;

import com.nevzatcirak.example.security.CustomRestTemplate;
import com.nevzatcirak.example.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class RestTemplateConfig {
    @Autowired
    TokenService tokenService;

    @Bean
    @RequestScope
    public CustomRestTemplate customRestTemplate(OAuth2AuthorizedClientService clientService, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = tokenService.getAccessToken().getTokenValue();
        return new CustomRestTemplate(accessToken);
    }
}
