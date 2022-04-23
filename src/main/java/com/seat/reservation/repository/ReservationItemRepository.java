package com.seat.reservation.repository;

import com.seat.reservation.domain.Reservation;
import com.seat.reservation.domain.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {
    // 추후 QueryDsl로 변경 예정
    List<ReservationItem> findByReservation(Reservation reservation);
}
