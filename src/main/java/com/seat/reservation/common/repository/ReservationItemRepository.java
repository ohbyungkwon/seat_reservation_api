package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {

}
