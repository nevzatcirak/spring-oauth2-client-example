package com.nevzatcirak.examples.oauth2client.util;

import com.nevzatcirak.examples.oauth2client.security.model.OAuthUser;
import com.nevzatcirak.examples.oauth2client.security.model.User;
import org.springframework.stereotype.Component;

/**
 * @author Nevzat ÇIRAK
 * @mail ncirak@havelsan.com.tr
 * Created by ncirak at 09/07/2020
 */
@Component
public class UserUtils {

    public User getUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setName("Dummy");
        user.setEmail("dummy@user.com");
        user.setSurname("User");
        user.setId(1L);
        user.setPassword("123456");
        return user;
    }

    public OAuthUser convertUser(User user) {
        return new OAuthUser(user);
    }
}
