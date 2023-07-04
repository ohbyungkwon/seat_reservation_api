package com.seat.reservation.common.controller;


import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * {@link com.seat.reservation.admin.controller.SeatAdminController}
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;


    /**
     * @param merchantRegNum
     * @param startDateTime
     * @return ResponseEntity<ResponseComDto>
     * - 가맹점 이용가능 좌석 조회
     */
    @GetMapping("/seat/merchant/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> searchUseAbleSeat(
            @PathVariable Integer merchantRegNum,
            @RequestParam String startDateTime){
        List<SeatDto.showByTime> seatList = seatService.searchUseAbleSeat(merchantRegNum, startDateTime);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("")
                        .resultObj(seatList)
                        .build(), HttpStatus.OK);
    }
}
