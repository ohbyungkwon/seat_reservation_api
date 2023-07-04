package com.seat.reservation.common.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String email){
        super(email + " NotFoundException");
    }
}
