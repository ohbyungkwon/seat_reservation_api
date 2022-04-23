package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.repository.custom.ReservationCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
    Page<Reservation> findByUser(User user, Pageable pageable);

    Page<Reservation> findByUserAndRegisterDateBetween(User user, LocalDateTime start, LocalDateTime end) ;
}
