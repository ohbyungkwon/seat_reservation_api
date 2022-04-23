package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.exception.NotFoundPrincipalException;
import com.seat.reservation.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class SecurityService {
    @Autowired
    private UserRepository userRepository;

    protected Optional<User> getUser(){
        Principal principal = (Principal) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new NotFoundPrincipalException("Principal 만료"));

        return userRepository.findById(principal.getName());
    }
}
