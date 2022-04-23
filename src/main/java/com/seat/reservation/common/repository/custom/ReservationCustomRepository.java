package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.dto.ReservationDto;

public interface ReservationCustomRepository {
    ReservationDto.show findReservationDetail(Long reservationId);
}
