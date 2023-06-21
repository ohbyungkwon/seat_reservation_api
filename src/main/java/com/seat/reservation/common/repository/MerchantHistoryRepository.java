package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.MerchantHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchantHistoryRepository extends JpaRepository<MerchantHistory, Long> {

    /* 날짜 범위에 따라 이력을 보는 쿼리 */
    List<MerchantHistory> findByRegisterDateBetween(LocalDateTime start, LocalDateTime end);
}
