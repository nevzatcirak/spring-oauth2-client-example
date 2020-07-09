package com.nevzatcirak.examples.oauth2client.security.oauth2;

import com.nevzatcirak.examples.oauth2client.security.exception.OAuth2AuthenticationProcessingException;
import com.nevzatcirak.examples.oauth2client.security.model.OAuthUser;
import com.nevzatcirak.examples.oauth2client.security.model.User;
import com.nevzatcirak.examples.oauth2client.security.model.oauth2.AuthProvider;
import com.nevzatcirak.examples.oauth2client.security.model.oauth2.OAuth2UserInfo;
import com.nevzatcirak.examples.oauth2client.security.model.oauth2.OAuth2UserInfoFactory;
import com.nevzatcirak.examples.oauth2client.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserUtils userUtils;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getUsername())) {
            throw new OAuth2AuthenticationProcessingException("Username not found from OAuth2 provider");
        }
        User user = userUtils.getUser(oAuth2UserInfo.getUsername());
        return OAuthUser.create(user, oAuth2User.getAttributes());
    }

}
