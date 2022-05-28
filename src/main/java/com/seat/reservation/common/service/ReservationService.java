package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;

public interface ReservationService {
    public Boolean saveReservation(ReservationDto.create dto);

    public ReservationDetailDto selectReservationDetail(Long reservationId);
}
