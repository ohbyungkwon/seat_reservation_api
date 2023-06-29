package com.seat.reservation.admin.service.impl;

import com.seat.reservation.admin.service.SeatAdminService;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.repository.Impl.SeatRepositoryImpl;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatAdminServiceImpl implements SeatAdminService {
    private final SeatRepository seatRepository;
    private final MerchantRepository merchantRepository;
    private final SeatRepositoryImpl seatRepositoryImpl;

    @Override
    public List<SeatDto.show> searchSeatsInMerchant(Integer merchantRegNum) {
        Merchant merchant = merchantRepository.findByMerchantRegNum(merchantRegNum);
        if(merchant == null){
            throw new BadReqException("존재하지 않는 가맹점입니다.");
        }

        return seatRepositoryImpl.findSeatInMerchant(merchantRegNum);
    }

    @Override
    public void createSeats(List<SeatDto.create> createSeats) {
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
    }

    @Override
    public String updateSeats(SeatDto.update updateSeat) {
        Seat seat = seatRepository.findById(updateSeat.getId())
                .orElseThrow(() -> new BadReqException("존재하지 않는 좌석입니다."));

        seat.setReservationCost(updateSeat.getReservationCost());
        seat.setRegisterCode(updateSeat.getRegisterCode());
        seatRepository.save(seat);

        return seat.getSeatCode() + "좌석 정보가" +
                (RegisterCode.DELETE.equals(updateSeat.getRegisterCode()) ? "삭제" : "수정") + "되었습니다.";
    }
}
