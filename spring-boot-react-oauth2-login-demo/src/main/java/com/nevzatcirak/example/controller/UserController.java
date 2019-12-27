package com.nevzatcirak.example.controller;

import com.nevzatcirak.example.exception.ResourceNotFoundException;
import com.nevzatcirak.example.model.User;
import com.nevzatcirak.example.repository.UserRepository;
import com.nevzatcirak.example.security.CurrentUser;
import com.nevzatcirak.example.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal, HttpServletRequest httpServletRequest) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
