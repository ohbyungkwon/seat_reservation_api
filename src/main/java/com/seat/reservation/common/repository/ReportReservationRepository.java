package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.ReportReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportReservationRepository extends JpaRepository<ReportReservation, Long> {
}
