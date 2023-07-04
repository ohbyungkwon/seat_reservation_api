package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.exception.NotFoundPrincipalException;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.support.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * This Service returns the current login user.
 */
public class SecurityService {

    protected UserDto.create getUserInfo() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new NotFoundPrincipalException("Principal 만료");
        }
        return (UserDto.create) authentication.getPrincipal();
    }

    protected Optional<User> getUser(){
        UserDto.create userDto = this.getUserInfo();
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        return userRepository.findByUserId(userDto.getUserId());
    }
}
