package com.kajal.user_service.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserInfo implements UserDetails {
    private String id;
    private String email;
    private String userName;
    private String password;
    private UserRole roles;

    public UserInfo(String id, String email, String userName, String password, UserRole roles){
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + roles.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }
}
