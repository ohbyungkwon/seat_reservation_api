package com.seat.reservation.common.controller;

import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.ReservationService;
import com.seat.reservation.common.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class PosUserController {
    private final SeatService seatService;
    private final ReservationService reservationService;

    /**
     * @param seatId
     * @return ResponseEntity<ResponseComDto>
     * - 워크인 손님 방문(자리 비활성화)
     */
    @GetMapping("/use/{seatId}")
    public ResponseEntity<ResponseComDto> visitCustomerAnotherRoute(@PathVariable Long seatId){
        Boolean isSuccess = seatService.switchFlagAsWalkIn(seatId);

        Map<String, Object> json = new HashMap<>();
        json.put("isSuccess", isSuccess);

        return new ResponseEntity<>(ResponseComDto.builder()
                .resultObj(json)
                .resultMsg("좌석을 비활성화 했습니다.")
                .build(), HttpStatus.OK);
    }

    /**
     * @param update
     * @return ResponseEntity<ResponseComDto>
     * - 손님 퇴장(예약 종료, 좌석 활성화)
     */
    @GetMapping("/unUse")
    public ResponseEntity<ResponseComDto> completeReservation(ReservationDto.update update){
        Boolean isSuccess;
        if(update.isWalkIn()){
            isSuccess = seatService.switchFlagAsWalkIn(update.getSeatId());
        } else {
            isSuccess = reservationService.completeReservation(update);
        }

        Map<String, Object> json = new HashMap<>();
        json.put("isSuccess", isSuccess);

        return new ResponseEntity<>(ResponseComDto.builder()
                .resultObj(json)
                .resultMsg("좌석을 활성화 했습니다.")
                .build(), HttpStatus.OK);
    }
}
