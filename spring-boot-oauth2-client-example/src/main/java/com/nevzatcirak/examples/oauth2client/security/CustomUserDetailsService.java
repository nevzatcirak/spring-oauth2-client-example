package com.nevzatcirak.examples.oauth2client.security;


import com.nevzatcirak.examples.oauth2client.security.model.AuthUser;
import com.nevzatcirak.examples.oauth2client.security.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = getUserDetails(username);
        if (user == null) {
            logger.debug("Username:" + username + " not found!");
            throw new UsernameNotFoundException("Username=" + username + " not found!");
        }
        logger.debug("Loaded: " + user);
        return convertUser(user);
    }

    private User getUserDetails(String username) {
        User user = new User();
        user.setUsername(username);
        user.setName("Dummy");
        user.setEmail("dummy@user.com");
        user.setSurname("User");
        user.setId(1L);
        user.setPassword("123456");
        return user;
    }

    private AuthUser convertUser(User user) {
        return new AuthUser(user);
    }

}