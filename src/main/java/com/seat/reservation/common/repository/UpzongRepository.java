package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.Upzong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpzongRepository extends JpaRepository<Upzong, Long> {
}
