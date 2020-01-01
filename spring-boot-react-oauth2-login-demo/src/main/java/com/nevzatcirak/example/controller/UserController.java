package com.nevzatcirak.example.controller;

import com.nevzatcirak.example.exception.ResourceNotFoundException;
import com.nevzatcirak.example.model.User;
import com.nevzatcirak.example.repository.UserRepository;
import com.nevzatcirak.example.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @AuthenticationPrincipal Jwt jwt, Authentication authentication) {
        if (userPrincipal != null)
            return userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        else if(jwt != null)
            return userRepository.findByEmail(jwt.getClaims().get("email").toString())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", jwt.getClaims().get("email").toString()));
        else
            return null;
    }
}
