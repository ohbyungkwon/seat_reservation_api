package com.seat.reservation.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotSupportHistoryException extends RuntimeException{
    public NotSupportHistoryException(String message) {
        super(message);
    }
}
