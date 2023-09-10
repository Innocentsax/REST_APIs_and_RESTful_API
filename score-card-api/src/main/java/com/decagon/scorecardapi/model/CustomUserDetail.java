package com.decagon.scorecardapi.model;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class CustomUserDetail implements UserDetails {
    private String userName;
    private String password;
    private Boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public CustomUserDetail(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new ArrayList<>(List.of(new SimpleGrantedAuthority(user.getRole().name())));
        this.isEnabled = user.getIsAccountActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
