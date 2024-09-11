package com.seat.reservation.common.security;


import com.seat.reservation.common.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Spring Security supports 'UserDetails'
 * it supports functions related to user information.
 * And it is used at the {@link com.seat.reservation.common.security.CustomAuthenticationProvider}
 * and {@link com.seat.reservation.common.security.UserDetailsServiceImpl}
 */
@Getter
@RequiredArgsConstructor
public class MyUserDetails implements UserDetails, OAuth2User {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getValue()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public String getPassword() {
        return user.getPw();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isNeedChangePw();
    }

    @Override
    public boolean isEnabled() {
        return !user.isLocked();
    }

    @Override
    public String getName() {
        return user.getName();
    }
}
