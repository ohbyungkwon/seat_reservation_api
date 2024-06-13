package com.seat.reservation.common.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CustomForbiddenException extends AccessDeniedException {
    public CustomForbiddenException(String msg){
        super(msg);
    }
}
