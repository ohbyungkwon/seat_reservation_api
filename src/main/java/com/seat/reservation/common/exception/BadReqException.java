package com.seat.reservation.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadReqException extends RuntimeException{
    public BadReqException(String message) {
        super(message);
    }
}
