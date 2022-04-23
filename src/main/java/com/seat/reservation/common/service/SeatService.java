package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.SeatHistory;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.SeatHistoryRepository;
import com.seat.reservation.common.repository.SeatRepository;
import com.seat.reservation.common.service.impl.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class SeatService extends SecurityService implements HistoryService {
    private final SeatRepository seatRepository;
    private final SeatHistoryRepository seatHistoryRepository;

    public SeatService(SeatRepository seatRepository, SeatHistoryRepository seatHistoryRepository) {
        this.seatRepository = seatRepository;
        this.seatHistoryRepository = seatHistoryRepository;
    }

    @Override
    public void historySave(Object entity) {
        Seat seat = (Seat) entity;
        Optional<Seat> beforeSeatOptional = seatRepository.findById(seat.getId());
        if(beforeSeatOptional.isPresent()) {
            log.info("SEAT HISTORY INSERT!!!");

            User user = this.getUser().orElseThrow(() ->
                    new NotFoundUserException("사용자를 찾을 수 없습니다."));

            Seat beforeSeat = beforeSeatOptional.get();
            SeatHistory seatHistory = SeatHistory.builder()
                    .seat(beforeSeat)
                    .user(user)
                    .registerCode(beforeSeat.getRegisterCode())
                    .registerDate(LocalDateTime.now())
                    .build();
            seatHistoryRepository.save(seatHistory);
        }
    }
}
