package com.seat.reservation.repository.custom;

import com.seat.reservation.domain.Reservation;

public interface ReservationCustomRepository {
    Reservation findMyReservation();
}
