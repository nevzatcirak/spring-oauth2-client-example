package com.nevzatcirak.examples.oauth2client.config;

import com.nevzatcirak.examples.oauth2client.security.CustomRestTemplate;
import com.nevzatcirak.examples.oauth2client.security.model.AuthProvider;
import com.nevzatcirak.examples.oauth2client.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 01/07/2020.
 */
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
        String accessToken = tokenService.getAccessTokenFromSecurityContext(AuthProvider.kapi.name());
        return new CustomRestTemplate(accessToken);
    }
}
