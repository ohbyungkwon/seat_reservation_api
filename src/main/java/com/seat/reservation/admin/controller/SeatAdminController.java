package com.seat.reservation.admin.controller;

import com.seat.reservation.admin.service.SeatAdminService;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@link com.seat.reservation.common.controller.SeatController}
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SeatAdminController {
    private final SeatAdminService seatAdminService;


    /**
     * @param merchantRegNum
     * @return ResponseEntity<ResponseComDto>
     * - 가맹점 보유 좌석 조회
     */
    @GetMapping(path = "/seat/merchant/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> searchSeat(@PathVariable Integer merchantRegNum){
        List<SeatDto.show> seats = seatAdminService.searchSeatsInMerchant(merchantRegNum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                .resultMsg(merchantRegNum + "에 대한 좌석 리스트입니다.")
                .resultObj(seats)
                .build());
    }

    /**
     * @param createSeats
     * @return ResponseEntity<ResponseComDto>
     * - 좌석 등록
     */
    @PostMapping(path = "/seat")
    public ResponseEntity<ResponseComDto> createSeat(@RequestBody List<SeatDto.create> createSeats){
        seatAdminService.createSeats(createSeats);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                .resultMsg(createSeats.size()+ "개 좌석이 등록되었습니다.")
                .resultObj(createSeats)
                .build());
    }

    /**
     * @param updateSeat
     * @return ResponseEntity<ResponseComDto>
     * - 좌석 수정
     */
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
