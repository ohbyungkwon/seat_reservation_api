package com.seat.reservation.admin.controller;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    private final MerchantRepository merchantRepository;

    // 좌석 등록
    @PostMapping(path = "/create-seat")
    public ResponseEntity<ResponseComDto> createSeat(@RequestBody SeatDto.create createSeat){
        Merchant merchant = merchantRepository.findByMerchantRegNum(createSeat.getMerchantRegNum());

        if(merchant == null){
            System.out.println("존재하지 않는 가맹점입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                    .resultMsg("존재하지 않는 가맹점입니다.")
                    .resultObj(createSeat).build());

        }

        Seat seat = Seat.createSeat(createSeat.getSeatCode()
                                , merchant
                                , createSeat.getReservationCost()
                                , createSeat.getRegisterCode());

        seatRepository.save(seat);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                .resultMsg(createSeat.getSeatCode() + "가 등록되었습니다.")
                .resultObj(createSeat).build());
    }

    // 좌석 업데이트
    @PutMapping(path = "/update-seat")
    public ResponseEntity<ResponseComDto> updateSeat(@RequestParam SeatDto.update updateSeat){
        Optional<Seat> seat = seatRepository.findById(updateSeat.getId());

        if(seat.isPresent()){
            seat.get().setReservationCost(updateSeat.getReservationCost());
            seat.get().setRegisterCode(updateSeat.getRegisterCode());
            seatRepository.save(seat.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                    .resultObj("존재하지 않는 좌석입니다.")
                    .resultObj(updateSeat).build());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                .resultObj(updateSeat.getId() + "가 갱신되었습니다.")
                .resultObj(updateSeat).build());
    }


    @DeleteMapping(path = "/delete-seat/{seat_id}")
    public ResponseEntity<ResponseComDto> deleteSeat(@PathVariable(value = "seat_id") Long seatId){
        Optional<Seat> seat = seatRepository.findById(seatId);

        if(seat.isPresent()){
            seat.get().setRegisterCode(RegisterCode.DELETE);
            seatRepository.save(seat.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                    .resultObj("존재하지 않는 좌석입니다.")
                    .resultObj(seatId).build());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultObj(seatId + "가 삭제되었습니다.")
                        .resultObj(seatId).build());
    }



}
