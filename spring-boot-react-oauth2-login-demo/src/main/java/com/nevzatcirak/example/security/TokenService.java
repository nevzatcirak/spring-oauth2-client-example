package com.nevzatcirak.example.security;

import com.nevzatcirak.example.config.AppProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private AppProperties appProperties;
    private  OAuth2AuthorizedClientService clientService;

    private OAuth2AccessToken accessToken;
    private OAuth2RefreshToken refreshToken;

    @Autowired
    public TokenService(AppProperties appProperties,  OAuth2AuthorizedClientService clientService) {
        this.appProperties = appProperties;
        this.clientService = clientService;
    }

    public String getToken(Authentication authentication) {
        String token = null;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthorizedClient oAuth2AuthorizedClient = clientService.loadAuthorizedClient(((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(), authentication.getName());
            token = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        }
        return token;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    /*public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }*/

    public String getEmail(String token){
        return "";
    }

    /*public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }*/

    public OAuth2AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(OAuth2AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(OAuth2RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
}
