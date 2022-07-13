package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
