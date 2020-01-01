package com.nevzatcirak.example.config;

import com.nevzatcirak.example.model.AuthProvider;
import com.nevzatcirak.example.security.CustomRestTemplate;
import com.nevzatcirak.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class RestTemplateConfig {
    private TokenService tokenService;

    @Autowired
    public RestTemplateConfig(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Bean
    @RequestScope
    public CustomRestTemplate customRestTemplate() {
        String accessToken = tokenService.getAccessTokenFromSecurityContext(AuthProvider.keycloak.name());
        return new CustomRestTemplate(accessToken);
    }
}
