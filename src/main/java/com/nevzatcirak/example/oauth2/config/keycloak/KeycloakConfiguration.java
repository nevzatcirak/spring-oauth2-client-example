package com.nevzatcirak.example.oauth2.config.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
@Configuration
public class KeycloakConfiguration {

    @Bean
    @ConfigurationProperties(prefix="spring.keycloak.admin")
    public KeycloakAdminProperties keycloakAdminProperties() {
        return new KeycloakAdminProperties();
    }

    @Bean
    public Keycloak keycloak(KeycloakAdminProperties keycloakAdminProperties) {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAdminProperties.getServerUrl())
                .realm(keycloakAdminProperties.getRealm())
                .username(keycloakAdminProperties.getUsername())
                .password(keycloakAdminProperties.getPassword())
                .clientId(keycloakAdminProperties.getClientId())
                .build();
    }

}
