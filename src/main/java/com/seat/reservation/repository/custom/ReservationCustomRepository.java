package com.seat.reservation.repository.custom;

import com.seat.reservation.dto.ReservationDto;

public interface ReservationCustomRepository {
    ReservationDto.show findReservationDetail(Long reservationId);
}
