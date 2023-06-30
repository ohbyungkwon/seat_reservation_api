package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.SeatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatService {
    Boolean visitCustomerAnotherRoute(Long seatId);
    List<SeatDto.showByTime> searchUseAbleSeat(int merchantRegNum, LocalDateTime startTime);
}
