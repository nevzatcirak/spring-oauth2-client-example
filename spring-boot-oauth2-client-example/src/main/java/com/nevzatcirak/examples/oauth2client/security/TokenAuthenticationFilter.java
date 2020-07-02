package com.nevzatcirak.examples.oauth2client.security;

import com.nevzatcirak.examples.oauth2client.security.model.AuthProvider;
import com.nevzatcirak.examples.oauth2client.security.model.OAuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 01/07/2020.
 */
public class TokenAuthenticationFilter extends OAuth2LoginAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public TokenAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService authorizedClientService) {
        super(clientRegistrationRepository, authorizedClientService);
    }

    public TokenAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService authorizedClientService, String filterProcessesUrl) {
        super(clientRegistrationRepository, authorizedClientService, filterProcessesUrl);
    }

    public TokenAuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository, String filterProcessesUrl) {
        super(clientRegistrationRepository, authorizedClientRepository, filterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = null;
        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) super.attemptAuthentication(request, response);
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                logger.warn("Access Token: " + jwt);
                String username = "nevzatcirak17@hotmail.com"; // This part will be extracted from token

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                oAuth2AuthenticationToken = new OAuth2AuthenticationToken(convert(userDetails), userDetails.getAuthorities(), authentication.getAuthorizedClientRegistrationId());
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        return oAuth2AuthenticationToken;
    }

    /*protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                String username = "nevzatcirak17@hotmail.com"; // This part will be extracted from token

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(convert(userDetails), userDetails.getAuthorities(), AuthProvider.kapi.name());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }*/

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private OAuthUser convert(UserDetails userDetails) {
        return new OAuthUser(userDetails);
    }
}
