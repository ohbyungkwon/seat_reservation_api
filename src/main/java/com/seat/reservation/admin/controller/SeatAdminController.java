package com.seat.reservation.admin.controller;

import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/* 가맹점 등록 후 좌석 등록, 삭제 기능 */
/* TODO
*  1. AOP(Intercepter)를 이용하여 권한 확인 (기본적으로 ADMIN 권한이 있어야 접근이 가능)
*  2. 각 Request에 맞는 로직 추가
* */
@RestController
@RequestMapping("/admin/seat")
@RequiredArgsConstructor
public class SeatAdminController {
    private final SeatRepository seatRepository;

    // 좌석 등록
    @RequestMapping(path = "/create-seat", method = RequestMethod.POST)
    public String createSeat(@RequestBody SeatDto.create createSeat){
        String response = new String();

       // Seat seat = Seat.createSeat(createSeat.)


        return createSeat.toString();
    }

    // 좌석 업데이트
    @RequestMapping(path = "/update-seat", method = RequestMethod.PUT)
    public String updateSeat(@RequestParam SeatDto.update updateSeat){
        String response = new String();


        return updateSeat.toString();
    }


    // 좌석 삭제 -- 이거는 강의보고 하자...... 기억이 안나네;;;;
    @RequestMapping(path = "/delete-seat", method = RequestMethod.DELETE)
    public String deleteSeat(){
        String response = new String();

        return response;
    }



}
