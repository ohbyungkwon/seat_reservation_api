package com.seat.reservation.common.controller;


import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/seat/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> searchUseAbleSeat(
            @PathVariable Integer merchantRegNum,
            @RequestParam LocalDateTime startTime){
        List<SeatDto.showByTime> seatList = seatService.searchUseAbleSeat(merchantRegNum, startTime);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("")
                        .resultObj(seatList)
                        .build(), HttpStatus.OK);
    }
}
