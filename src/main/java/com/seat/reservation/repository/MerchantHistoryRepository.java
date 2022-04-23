package com.seat.reservation.repository;

import com.seat.reservation.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchantHistoryRepository extends JpaRepository<MerchantHistory, Long> {

    /* 가게의 등록, 수정, 삭제 이력을 가져오는 쿼리 */
    List<MerchantHistory> findBySeat(Merchant merchant);

    /* 유저가 등록, 수정, 삭제한 이력을 가져오는 쿼리 */
    List<MerchantHistory> findByUser(User user);

    /* 날짜 범위에 따라 이력을 보는 쿼리 */
    List<MerchantHistory> findByRegisterDateBetween(LocalDateTime start, LocalDateTime end);
}
