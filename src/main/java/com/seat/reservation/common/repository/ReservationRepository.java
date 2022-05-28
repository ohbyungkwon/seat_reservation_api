package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.repository.custom.ReservationRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    Page<Reservation> findByUser(User user, Pageable pageable);

    Page<Reservation> findByUserAndRegisterDateBetween(User user, LocalDateTime start, LocalDateTime end, Pageable pageable) ;
    Boolean existsByIdAndUser(Long reservationId, User user);
}
