package com.nevzatcirak.examples.oauth2client.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 01/01/2020.
 */
public interface TokenService {
    /**
     * Loads Access Token via SecurityContextHolder.getContext().getAuthentication()
     * @param clientRegistrationId
     * @return String Value of Access Token
     */
    String getAccessTokenFromSecurityContext(String clientRegistrationId);

    /**
     * Loads access token from authentication object
     * @param clientRegistrationId
     * @param authentication
     * @return String Value of Access Token
     */
    String getAccessTokenValue(String clientRegistrationId, Authentication authentication);

    /**
     * Loads access token from authentication object
     * @param clientRegistrationId
     * @param authentication
     * @return OAuth2 Access Token
     */
    OAuth2AccessToken getAccessToken(String clientRegistrationId, Authentication authentication);

    /**
     * Loads Refresh Token via SecurityContextHolder.getContext().getAuthentication()
     * @param clientRegistrationId
     * @return String Value of Refresh Token
     */
    String getRefreshTokenFromSecurityContext(String clientRegistrationId);

    /**
     * Loads refresh token from authentication object
     * @param clientRegistrationId
     * @param authentication
     * @return String refresh of Access Token
     */
    String getRefreshTokenValue(String clientRegistrationId, Authentication authentication);

    /**
     * Loads refresh token from authentication object
     * @param clientRegistrationId
     * @param authentication
     * @return OAuth2 Refresh Token
     */
    OAuth2RefreshToken getRefreshToken(String clientRegistrationId, Authentication authentication);

    /**
     * Loads Authorized Client via SecurityContextHolder.getContext().getAuthentication()
     * @param clientRegistrationId
     * @return OAuth2 Authorized Client
     */
    OAuth2AuthorizedClient getAuthorizedClient(String clientRegistrationId);

    /**
     * Loads Authorized Client from authentication object
     * @param clientRegistrationId
     * @param authentication
     * @return OAuth2 Authorized Client
     */
    OAuth2AuthorizedClient getAuthorizedClient(String clientRegistrationId, Authentication authentication);

}
