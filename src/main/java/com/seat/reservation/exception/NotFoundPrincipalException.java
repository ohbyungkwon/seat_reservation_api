package com.seat.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundPrincipalException extends RuntimeException{
    public NotFoundPrincipalException(String message) {
        super(message);
    }
}
