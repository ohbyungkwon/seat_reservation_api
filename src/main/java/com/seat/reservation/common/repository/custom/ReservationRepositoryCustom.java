package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.Reservation;

public interface ReservationRepositoryCustom {
    Reservation findReservationDetail(Long reservationId);
}
