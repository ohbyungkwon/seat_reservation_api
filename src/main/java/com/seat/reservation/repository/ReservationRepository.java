package com.seat.reservation.repository;

import com.seat.reservation.domain.Reservation;
import com.seat.reservation.repository.custom.ReservationCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Long, Reservation>, ReservationCustomRepository {

}
