package com.seat.reservation.common.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String msg){
        super(msg);
    }
}
