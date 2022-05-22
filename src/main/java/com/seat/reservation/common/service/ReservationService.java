package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.ReservationDetailDto;

public interface ReservationService {

    public ReservationDetailDto selectReservationDetail(Long reservationId);
}
