package com.nevzatcirak.example.config;

import com.nevzatcirak.example.model.AuthProvider;
import com.nevzatcirak.example.security.CustomRestTemplate;
import com.nevzatcirak.example.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Configuration
public class RestTemplateConfig {
    @Autowired
    TokenProvider tokenProvider;

    @Bean
    @RequestScope
    public CustomRestTemplate customRestTemplate(OAuth2AuthorizedClientService clientService, HttpServletRequest request) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = tokenProvider.getJwtFromRequest(request);
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication authentication = (OAuth2Authentication) principal;
            Object details = authentication.getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                OAuth2AuthenticationDetails oauthsDetails = (OAuth2AuthenticationDetails) details;
                accessToken = oauthsDetails.getTokenValue();
            }
        }
        return new CustomRestTemplate(accessToken);
    }
}
