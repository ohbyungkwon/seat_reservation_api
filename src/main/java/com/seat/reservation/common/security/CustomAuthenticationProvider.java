package com.seat.reservation.common.security;

import com.seat.reservation.common.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userName = token.getName();
        String userPw = (String)token.getCredentials();

        MyUserDetails userDetails = userDetailService.loadUserByUsername(userName);
        if(!passwordEncoder.matches(userPw, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "use invalid password. please check it.");
        }

        UserDto.create user = userDetails.getUser().convertDto();
        return new UsernamePasswordAuthenticationToken(user, userPw, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
