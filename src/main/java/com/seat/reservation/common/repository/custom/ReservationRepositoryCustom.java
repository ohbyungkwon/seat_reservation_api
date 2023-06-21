package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ReservationRepositoryCustom {
    Reservation findReservationDetail(Long reservationId);

    Page<Reservation> findByUserAndRegisterDateBetween(String userId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
