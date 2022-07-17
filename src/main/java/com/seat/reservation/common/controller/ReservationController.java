package com.seat.reservation.common.controller;

import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SearchDto;
import com.seat.reservation.common.service.ReservationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/common")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation")
    public ResponseEntity<ResponseComDto> saveReservation(ReservationDto.create create){
        Boolean isSuccess = reservationService.saveReservation(create);

        Map<String, Boolean> json = new HashMap<>();
        json.put("isSuccess", isSuccess);
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("예약 완료되었습니다.")
                        .resultObj(json)
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<ResponseComDto> removeReservation(@PathVariable Long id) {
        Boolean isSuccess = reservationService.removeReservation(id);

        Map<String, Boolean> json = new HashMap<>();
        json.put("isSuccess", isSuccess);
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("예약 취소되었습니다.")
                        .resultObj(json)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<ResponseComDto> selectReservations(SearchDto.date search, Pageable pageable){
        Page<ReservationDto.show> reservations = reservationService.selectReservations(search, pageable);
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("나의 예약 리스트 조회.")
                        .resultObj(reservations)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ResponseComDto> selectReservationDetail(@PathVariable Long id){
        ReservationDetailDto reservationDetail = reservationService.selectReservationDetail(id);
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("예약 상세 조회.")
                        .resultObj(reservationDetail)
                        .build(), HttpStatus.OK);
    }
}
