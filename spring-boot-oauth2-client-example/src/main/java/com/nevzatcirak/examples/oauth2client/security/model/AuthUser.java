package com.nevzatcirak.examples.oauth2client.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Nevzat ÇIRAK
 * @mail ncirak@havelsan.com.tr
 * Created by ncirak at 29/06/2020
 */
public class AuthUser extends User implements UserDetails {
    public AuthUser(User user) {
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
//        getRoles().forEach(role -> {
//            role.getPermissions().forEach(permission -> authorities.add((GrantedAuthority) permission::getName));
//        });
        return authorities;
    }

}
