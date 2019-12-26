package com.nevzatcirak.example.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class OAuth2ClientSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login").defaultSuccessUrl("/index").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout").permitAll()
                .and()
                .csrf().disable()
                .oauth2Client();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.withUsername("user1")
                .password("123456")
                .roles("USER")
                .build();
        return  new InMemoryUserDetailsManager(user);
    }

}
