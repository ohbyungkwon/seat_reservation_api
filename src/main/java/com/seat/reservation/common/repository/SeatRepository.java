package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    /* 가맹점에 등록된 좌석 조회 쿼리 */
    Page<Seat> findByMerchant(Merchant merchant, Pageable pageable);

    /* 좌석 삭제 쿼리 */
    void deleteBySeatCode(String seatCode);
}
