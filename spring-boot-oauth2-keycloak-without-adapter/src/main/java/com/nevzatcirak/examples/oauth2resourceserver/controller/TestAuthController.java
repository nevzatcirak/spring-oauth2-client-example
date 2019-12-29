package com.nevzatcirak.examples.oauth2resourceserver.controller;

import com.nevzatcirak.examples.oauth2resourceserver.config.security.GrantedAuthoritiesExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthController {

    @Autowired
    GrantedAuthoritiesExtractor grantedAuthoritiesExtractor;

    @GetMapping("/principal")
    @PreAuthorize("hasAuthority('SCOPE_PRINCIPAL')")
    public String getPrincipal(JwtAuthenticationToken principal) {
        return principal.toString();
    }

    @GetMapping("/authentication")
    @PreAuthorize("hasAuthority('SCOPE_AUTHENTICATION')")
    public String getAuthentication(Authentication authentication) {
        return authentication.toString();
    }

    @GetMapping("/testinfo")
//    @PreAuthorize("hasAuthority('ROLE_READ_TEST_RESOURCE_SERVICE')")
    public String getTestInfo() {
        return "OK. You have permission! :)";
    }
}
