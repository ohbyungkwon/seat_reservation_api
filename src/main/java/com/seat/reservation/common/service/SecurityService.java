package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.exception.NotFoundPrincipalException;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.support.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class SecurityService {

    protected Optional<User> getUser(){
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);

        Principal principal = (Principal) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new NotFoundPrincipalException("Principal 만료"));

        return userRepository.findById(principal.getName());
    }
}
