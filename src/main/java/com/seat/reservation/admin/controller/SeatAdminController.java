package com.seat.reservation.admin.controller;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.repository.Impl.SeatRepositoryImpl;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* 가맹점 등록 후 좌석 등록, 삭제 기능 */
/* TODO
*  1. AOP(Intercepter)를 이용하여 권한 확인 (기본적으로 ADMIN 권한이 있어야 접근이 가능)
*  2. 각 Request에 맞는 로직 추가
* */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SeatAdminController {
    private final SeatRepository seatRepository;
    private final MerchantRepository merchantRepository;
    private final SeatRepositoryImpl seatRepositoryImpl;

    @GetMapping(path = "/seat/merchant/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> searchSeat(@PathVariable Integer merchantRegNum){
        Merchant merchant = merchantRepository.findByMerchantRegNum(merchantRegNum);
        if(merchant == null){
            throw new BadReqException("존재하지 않는 가맹점입니다.");
        }

        List<SeatDto.show> seats = seatRepositoryImpl.findSeatInMerchant(merchantRegNum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                .resultMsg(merchantRegNum + "에 대한 좌석 리스트입니다.")
                .resultObj(seats)
                .build());
    }

    // 좌석 등록
    @PostMapping(path = "/seat")
    public ResponseEntity<ResponseComDto> createSeat(@RequestBody List<SeatDto.create> createSeats){
        Merchant merchant = merchantRepository.findByMerchantRegNum(createSeats.get(0).getMerchantRegNum());
        if(merchant == null){
            throw new BadReqException("존재하지 않는 가맹점입니다.");
        }

        List<Seat> seatList = new ArrayList<>();
        createSeats.forEach((createSeat) -> {
            seatList.add(Seat.createSeat(createSeat.getSeatCode()
                    , merchant
                    , createSeat.getReservationCost()
                    , createSeat.getRegisterCode()));
        });
        seatRepository.saveAll(seatList);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                .resultMsg(seatList.size()+ "개 좌석이 등록되었습니다.")
                .resultObj(createSeats)
                .build());
    }

    // 좌석 업데이트
    @PutMapping(path = "/seat")
    public ResponseEntity<ResponseComDto> updateSeat(@RequestParam SeatDto.update updateSeat){
        Seat seat = seatRepository.findById(updateSeat.getId())
                .orElseThrow(() -> new BadReqException("존재하지 않는 좌석입니다."));

        seat.setReservationCost(updateSeat.getReservationCost());
        seat.setRegisterCode(updateSeat.getRegisterCode());
        seatRepository.save(seat);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                .resultMsg(seat.getSeatCode() + "번 좌석 정보가 갱신되었습니다.")
                .resultObj(updateSeat)
                .build());
    }


    @DeleteMapping(path = "/seats/{seatId}")
    public ResponseEntity<ResponseComDto> deleteSeat(@PathVariable Long seatId){
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new BadReqException("존재하지 않는 좌석입니다."));

        seat.setRegisterCode(RegisterCode.DELETE);
        seatRepository.save(seat);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultMsg(seat.getSeatCode() + "번 좌석이 삭제되었습니다.")
                        .resultObj(null)
                        .build());
    }
}
