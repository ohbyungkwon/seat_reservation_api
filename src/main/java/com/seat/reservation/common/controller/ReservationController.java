package com.seat.reservation.common.controller;

import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/common")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation")
    public ResponseEntity<ResponseComDto> save(ReservationDto.create create){
        Boolean isSuccess = reservationService.saveReservation(create);
//        return new ResponseEntity<>(
//                ResponseComDto.builder()
//                        .
//
//        );
        return null;
    }
}
