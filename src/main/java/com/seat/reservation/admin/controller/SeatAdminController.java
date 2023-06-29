package com.seat.reservation.admin.controller;

import com.seat.reservation.admin.service.SeatAdminService;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* 가맹점 등록 후 좌석 등록, 삭제 기능 */
/* TODO
*  1. AOP(Intercepter)를 이용하여 권한 확인 (기본적으로 ADMIN 권한이 있어야 접근이 가능)
*  2. 각 Request에 맞는 로직 추가
* */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SeatAdminController {
    private final SeatAdminService seatAdminService;

    @GetMapping(path = "/seat/merchant/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> searchSeat(@PathVariable Integer merchantRegNum){
        List<SeatDto.show> seats = seatAdminService.searchSeatsInMerchant(merchantRegNum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                .resultMsg(merchantRegNum + "에 대한 좌석 리스트입니다.")
                .resultObj(seats)
                .build());
    }

    // 좌석 등록
    @PostMapping(path = "/seat")
    public ResponseEntity<ResponseComDto> createSeat(@RequestBody List<SeatDto.create> createSeats){
        seatAdminService.createSeats(createSeats);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                .resultMsg(createSeats.size()+ "개 좌석이 등록되었습니다.")
                .resultObj(createSeats)
                .build());
    }

    // 좌석 업데이트
    @PutMapping(path = "/seat")
    public ResponseEntity<ResponseComDto> updateSeat(@RequestParam SeatDto.update updateSeat){
        String msg = seatAdminService.updateSeats(updateSeat);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                .resultMsg(msg)
                .resultObj(updateSeat)
                .build());
    }
}
