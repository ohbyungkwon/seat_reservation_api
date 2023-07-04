package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.SeatDto;

import java.util.List;

/**
 * {@link com.seat.reservation.common.service.impl.SeatServiceImpl}
 */
public interface SeatService {
    Boolean switchFlagAsWalkIn(Long seatId);
    List<SeatDto.showByTime> searchUseAbleSeat(int merchantRegNum, String startDateTime);
}
