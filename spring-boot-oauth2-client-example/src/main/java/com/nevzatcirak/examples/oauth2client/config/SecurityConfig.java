package com.nevzatcirak.examples.oauth2client.config;

import com.nevzatcirak.examples.oauth2client.security.CustomUserDetailsService;
import com.nevzatcirak.examples.oauth2client.security.TokenAuthenticationFilter;
import com.nevzatcirak.examples.oauth2client.security.model.AuthProvider;
import com.nevzatcirak.examples.oauth2client.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.nevzatcirak.examples.oauth2client.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.nevzatcirak.examples.oauth2client.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 01/07/2020.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@PropertySource("classpath:application.yml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private String issuerUri;
    private String clientId;
    private String clientSecret;

    @Autowired
    public SecurityConfig(@Value("${auth.issuer-uri}") String issuerUri,
                          @Value("${auth.client-id}") String clientId,
                          @Value("${auth.client-secret}") String clientSecret) {
        this.issuerUri = issuerUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

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

    @Bean
    OAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.keycloakClientRegistration());
    }

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
//                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
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
                                                .userService(defaultOAuth2UserService())
                                )
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .oauth2Client(oauth2Client ->
                        oauth2Client
                                .authorizationCodeGrant()
                                .accessTokenResponseClient(this.defaultAuthorizationCodeTokenResponseClient())
                );

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), OAuth2LoginAuthenticationFilter.class);
    }

    private ClientRegistration keycloakClientRegistration() {
        return ClientRegistration.withRegistrationId(AuthProvider.kapi.name())
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate("{baseUrl}/oauth2/callback/{registrationId}")
                .scope("user")
                .authorizationUri(issuerUri + "/protocol/openid-connect/auth")
                .tokenUri(issuerUri + "/protocol/openid-connect/token")
                .userInfoUri(issuerUri + "/protocol/openid-connect/userinfo")
                .userNameAttributeName("preferred_username")
                .jwkSetUri(issuerUri + "/protocol/openid-connect/certs")
                .clientName("Kapi")
                .build();
    }
}
