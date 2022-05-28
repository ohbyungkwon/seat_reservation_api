package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.dto.SeatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatRepositoryCustom {
    List<SeatDto.show> findSeatInMerchant(int merchantRegNumber);
    List<SeatDto.showByTime> findSeatByTime(int merchantRegNum, LocalDateTime startTime, LocalDateTime endTime);
}
