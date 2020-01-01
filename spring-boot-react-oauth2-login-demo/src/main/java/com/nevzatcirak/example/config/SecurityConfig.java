package com.nevzatcirak.example.config;

import com.nevzatcirak.example.security.CustomUserDetailsService;
import com.nevzatcirak.example.security.GrantedAuthoritiesExtractor;
import com.nevzatcirak.example.security.RestAuthenticationEntryPoint;
import com.nevzatcirak.example.security.oauth2.CustomOAuth2UserService;
import com.nevzatcirak.example.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.nevzatcirak.example.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.nevzatcirak.example.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.RefreshTokenOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.DefaultRefreshTokenTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    /*@Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }*/

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RefreshTokenOAuth2AuthorizedClientProvider refreshTokenOAuth2AuthorizedClientProvider() {
        RefreshTokenOAuth2AuthorizedClientProvider refreshTokenOAuth2AuthorizedClientProvider = new RefreshTokenOAuth2AuthorizedClientProvider();
        refreshTokenOAuth2AuthorizedClientProvider.setAccessTokenResponseClient(this.defaultRefreshTokenTokenResponseClient());
        return new RefreshTokenOAuth2AuthorizedClientProvider();
    }

    @Bean
    public DefaultAuthorizationCodeTokenResponseClient defaultAuthorizationCodeTokenResponseClient() {
        return new DefaultAuthorizationCodeTokenResponseClient();
    }

    @Bean
    public DefaultRefreshTokenTokenResponseClient defaultRefreshTokenTokenResponseClient() {
        return new DefaultRefreshTokenTokenResponseClient();
    }


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * The OAuth2AuthorizedClientManager is responsible for managing the authorization (or re-authorization)
     * of an OAuth 2.0 Client, in collaboration with one or more OAuth2AuthorizedClientProvider(s) and
     * associate it with an OAuth2AuthorizedClientProvider composite that provides support for the
     * authorization_code, refresh_token, client_credentials and password authorization grant types
     *
     * @param clientRegistrationRepository
     * @param authorizedClientRepository
     * @return
     */
    @Bean
    public OAuth2AuthorizedClientManager auth2AuthorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .clientCredentials()
                        .password()
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    /************************Resource Server Configurations*********************************/

    @Bean
    Converter<Jwt, AbstractAuthenticationToken> grantedAuthoritiesExtractorConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesExtractor());
        return jwtAuthenticationConverter;
    }

    @Bean
    GrantedAuthoritiesExtractor grantedAuthoritiesExtractor() {
        return new GrantedAuthoritiesExtractor();
    }

    /***************************************************************************************/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/auth/**", "/oauth2/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .authorizationEndpoint(authorizationEndpoint ->
                                        authorizationEndpoint
                                                .baseUri("/oauth2/authorize")
                                                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                                )
                                .redirectionEndpoint(redirectionEndpoint ->
                                        redirectionEndpoint
                                                .baseUri("/oauth2/callback/*")
                                )
                                .tokenEndpoint(tokenEndpoint ->
                                        tokenEndpoint
                                                .accessTokenResponseClient(this.defaultAuthorizationCodeTokenResponseClient())
                                )
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(customOAuth2UserService)
                                )
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .oauth2Client(oauth2Client ->
                        oauth2Client
                                .authorizationCodeGrant()
                                .accessTokenResponseClient(this.defaultAuthorizationCodeTokenResponseClient())
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt()
                                .jwtAuthenticationConverter(grantedAuthoritiesExtractorConverter())
                );

        // Add our custom Token based authentication filter
//        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
