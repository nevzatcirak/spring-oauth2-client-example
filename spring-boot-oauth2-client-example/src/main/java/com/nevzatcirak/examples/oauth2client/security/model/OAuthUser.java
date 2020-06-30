package com.nevzatcirak.examples.oauth2client.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Nevzat ÇIRAK
 * @mail ncirak@havelsan.com.tr
 * Created by ncirak at 30/06/2020
 */
public class OAuthUser extends User implements OAuth2User, UserDetails {
    public OAuthUser(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setName(user.getName());
        this.setSurname(user.getSurname());
        this.setEmail(user.getEmail());
        this.setAccountNonExpired(user.isAccountNonExpired());
        this.setAccountNonLocked(user.isAccountNonLocked());
        this.setCredentialsNonExpired(user.isCredentialsNonExpired());
//        this.setEnabled(user.isEnabled());
//        this.setCustomFields(user.getCustomFields());
//        this.setRoles(user.getRoles());
//        this.setUserSettings(user.getUserSettings());
    }

    public OAuthUser(UserDetails user) {
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setAccountNonExpired(user.isAccountNonExpired());
        this.setAccountNonLocked(user.isAccountNonLocked());
        this.setCredentialsNonExpired(user.isCredentialsNonExpired());
//        this.setEnabled(user.isEnabled());
//        this.setCustomFields(user.getCustomFields());
//        this.setRoles(user.getRoles());
//        this.setUserSettings(user.getUserSettings());
    }

    @Override
    public <A> A getAttribute(String name) {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
//        getRoles().forEach(role -> {
//            role.getPermissions().forEach(permission -> authorities.add((GrantedAuthority) permission::getName));
//        });
        return authorities;
    }
}
