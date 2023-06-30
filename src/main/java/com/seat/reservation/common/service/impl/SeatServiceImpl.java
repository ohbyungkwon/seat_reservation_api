package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.SeatHistory;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.SeatHistoryRepository;
import com.seat.reservation.common.repository.SeatRepository;
import com.seat.reservation.common.service.HistoryService;
import com.seat.reservation.common.service.SeatService;
import com.seat.reservation.common.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SeatServiceImpl extends SecurityService implements HistoryService, SeatService {
    private final SeatRepository seatRepository;
    private final SeatHistoryRepository seatHistoryRepository;

    public SeatServiceImpl(SeatRepository seatRepository, SeatHistoryRepository seatHistoryRepository) {
        this.seatRepository = seatRepository;
        this.seatHistoryRepository = seatHistoryRepository;
    }

    @Override
    @Transactional
    public void historySave(Object entity) {
        Seat seat = (Seat) entity;
        Optional<Seat> beforeSeatOptional = seatRepository.findById(seat.getId());
        if(beforeSeatOptional.isPresent()) {
            log.info("SEAT HISTORY INSERT!!!");

            User user = this.getUser().orElseThrow(() ->
                    new NotFoundUserException("사용자를 찾을 수 없습니다."));

            Seat beforeSeat = beforeSeatOptional.get();
            SeatHistory seatHistory = SeatHistory.builder()
                    .seatId(beforeSeat.getId())
                    .user(user)
                    .registerCode(RegisterCode.CHANGE)
                    .registerDate(LocalDateTime.now())
                    .build();
            seatHistoryRepository.save(seatHistory);
        }
    }

    @Override
    @Transactional
    public Boolean visitCustomerAnotherRoute(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new BadReqException("좌석 정보를 확인해주세요."));
        seat.setIsUse(Boolean.TRUE);
        return Boolean.TRUE;
    }


    public List<SeatDto.showByTime> searchUseAbleSeat(int merchantRegNum, String startDateTimeStr) {
        LocalDateTime startDateTime;
        if(startDateTimeStr == null) {
            startDateTime = LocalDateTime.now();
        } else {
            startDateTime = LocalDateTime.parse(startDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return seatRepository.findSeatByTime(merchantRegNum, startDateTime);
    }
}
