package com.seat.reservation.common.security;

import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import com.seat.reservation.common.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if(!userDetails.isEnabled()) {
            throw new CustomAuthenticationException("계정이 잠겨있습니다.");
        }

        if(!passwordEncoder.matches(userPw, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "use invalid password. please check it.");
        }

        UserDto.create user = userDetails.getUser().convertDto();
        Authentication workedAuthentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(workedAuthentication);// Session 사용시 설정 필요
        return workedAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
