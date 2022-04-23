package com.seat.reservation.repository;

import com.seat.reservation.domain.Reservation;
import com.seat.reservation.domain.User;
import com.seat.reservation.repository.custom.ReservationCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
    Page<Reservation> findByUser(User user, Pageable pageable);

    Page<Reservation> findByUserAndRegisterDateBetween(User user, Date start, Date end) ;
}
