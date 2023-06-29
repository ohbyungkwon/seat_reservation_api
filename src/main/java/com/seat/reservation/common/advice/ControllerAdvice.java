package com.seat.reservation.common.advice;

import com.seat.reservation.common.dto.ResponseComDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseComDto> responseForException(Exception e) {
        String msg = e.getMessage();
        String exceptionName = e.getClass().getSimpleName();

        ResponseStatus status = e.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus httpStatus = status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status.value();

        log.info("#############################");
        log.info("Exception Kind: {}", exceptionName);
        log.info("Exception Msg: {}", msg);
        log.info("Exception Status: {}", status);
        log.info("#############################");

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg(msg)
                        .resultObj(exceptionName)
                        .build(), httpStatus);
    }
}
