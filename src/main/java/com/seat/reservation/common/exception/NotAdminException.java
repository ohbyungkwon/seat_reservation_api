package com.seat.reservation.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAdminException extends RuntimeException{
    public NotAdminException(String message) {
        super(message);
    }
}
