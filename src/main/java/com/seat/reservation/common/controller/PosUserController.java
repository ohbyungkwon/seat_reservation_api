package com.seat.reservation.common.controller;

import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.ReservationService;
import com.seat.reservation.common.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/common")
@RequiredArgsConstructor
public class PosUserController {
    private final SeatService seatService;
    private final ReservationService reservationService;

    @GetMapping("/use/{seatId}")
    public ResponseEntity<ResponseComDto> visitCustomerAnotherRoute(@PathVariable Long seatId){
        Boolean isSuccess = seatService.visitCustomerAnotherRoute(seatId);

        Map<String, Object> json = new HashMap<>();
        json.put("isSuccess", isSuccess);

        return new ResponseEntity<>(ResponseComDto.builder()
                .resultObj(json)
                .resultMsg("좌석을 비활성화 했습니다.")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/unUse")
    public ResponseEntity<ResponseComDto> completePreReservation(ReservationDto.update update){
        Boolean isSuccess = reservationService.completePreReservation(update);

        Map<String, Object> json = new HashMap<>();
        json.put("isSuccess", isSuccess);

        return new ResponseEntity<>(ResponseComDto.builder()
                .resultObj(json)
                .resultMsg("좌석을 활성화 했습니다.")
                .build(), HttpStatus.OK);
    }
}
