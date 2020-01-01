package com.nevzatcirak.example.service.impl;

import com.nevzatcirak.example.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 01/01/2020.
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public TokenServiceImpl(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public String getAccessTokenFromSecurityContext(String clientRegistrationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAccessTokenValue(clientRegistrationId, authentication);
    }

    @Override
    public String getAccessTokenValue(String clientRegistrationId, Authentication authentication) {
        OAuth2AccessToken accessToken = getAccessToken(clientRegistrationId, authentication);
        return accessToken.getTokenValue();
    }

    @Override
    public OAuth2AccessToken getAccessToken(String clientRegistrationId, Authentication authentication) {
        if (!Objects.isNull(authentication)) {
            OAuth2AuthorizedClient authorizedClient =
                    this.authorizedClientService.loadAuthorizedClient(clientRegistrationId, authentication.getName());
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
            return accessToken;
        }
        logger.warn("Authentication object is null. Please check authentication status.");
        return null;
    }

    @Override
    public String getRefreshTokenFromSecurityContext(String clientRegistrationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getRefreshTokenValue(clientRegistrationId, authentication);
    }

    @Override
    public String getRefreshTokenValue(String clientRegistrationId, Authentication authentication) {
        OAuth2RefreshToken refreshToken = getRefreshToken(clientRegistrationId, authentication);
        return refreshToken.getTokenValue();
    }

    @Override
    public OAuth2RefreshToken getRefreshToken(String clientRegistrationId, Authentication authentication) {
        if (!Objects.isNull(authentication)) {
            OAuth2AuthorizedClient authorizedClient =
                    this.authorizedClientService.loadAuthorizedClient(clientRegistrationId, authentication.getName());
            OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();
            return refreshToken;
        }
        logger.warn("Authentication object is null. Please check authentication status.");
        return null;
    }

    @Override
    public OAuth2AuthorizedClient getAuthorizedClient(String clientRegistrationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAuthorizedClient(clientRegistrationId, authentication);
    }

    @Override
    public OAuth2AuthorizedClient getAuthorizedClient(String clientRegistrationId, Authentication authentication) {
        if (!Objects.isNull(authentication)) {
            OAuth2AuthorizedClient authorizedClient =
                    this.authorizedClientService.loadAuthorizedClient(clientRegistrationId, authentication.getName());
            return authorizedClient;
        }
        logger.warn("Authentication object is null. Please check authentication status.");
        return null;
    }
}
