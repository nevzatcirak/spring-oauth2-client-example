package com.nevzatcirak.example.oauth2.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
public class OAuth2ServerConfigProperties implements InitializingBean {
    @Value("${server.port}")
    private int serverPort;

    @Value("${rest.security.realm}")
    private String realm;

    @Autowired
    @Qualifier("authorizationCodeClient")
    private OAuth2ClientConfigProperties authorizationCodeClient;

    @Autowired
    @Qualifier("clientCredentialsClient")
    private OAuth2ClientConfigProperties clientCredentialsClient;

    @Autowired
    @Qualifier("passwordClient")
    private OAuth2ClientConfigProperties passwordClient;

    private final List<OAuth2ClientConfigProperties> clients = new ArrayList<>();

    @Bean
    @ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.client-authorization-code")
    protected OAuth2ClientConfigProperties authorizationCodeClient() {
        return new OAuth2ClientConfigProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.client-client-credentials")
    protected OAuth2ClientConfigProperties clientCredentialsClient() {
        return new OAuth2ClientConfigProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.client-password")
    protected OAuth2ClientConfigProperties passwordClient() {
        return new OAuth2ClientConfigProperties();
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getRealm() {
        return realm;
    }

    public OAuth2ClientConfigProperties getAuthorizationCodeClient() {
        return authorizationCodeClient;
    }

    public OAuth2ClientConfigProperties getClientCredentialsClient() {
        return clientCredentialsClient;
    }

    public OAuth2ClientConfigProperties getPasswordClient() {
        return passwordClient;
    }

    public List<OAuth2ClientConfigProperties> getClients() {
        return clients;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        clients.add(authorizationCodeClient);
        clients.add(clientCredentialsClient);
        clients.add(passwordClient);
    }

    public static class OAuth2ClientConfigProperties {

        private String clientId;

        private String clientSecret;

        private String authorizationGrantType;

        private String scope;

        private String description;

        private String demoUsername;

        private String demoPassword;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getAuthorizationGrantType() {
            return authorizationGrantType;
        }

        public void setAuthorizationGrantType(String authorizationGrantType) {
            this.authorizationGrantType = authorizationGrantType;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDemoUsername() {
            return demoUsername;
        }

        public void setDemoUsername(String demoUsername) {
            this.demoUsername = demoUsername;
        }

        public String getDemoPassword() {
            return demoPassword;
        }

        public void setDemoPassword(String demoPassword) {
            this.demoPassword = demoPassword;
        }

    }
}
