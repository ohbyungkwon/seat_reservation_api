package com.seat.reservation.common.advice;

import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.exception.NotAdminException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminControllerAdvice {

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ResponseComDto> responseAdminException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseComDto.builder()
                .resultMsg("Admin 계정이 아닙니다.")
                .resultObj(null).build());
    }
}
