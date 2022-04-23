package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    /* 가맹점에 등록된 좌석 조회 쿼리 */
    List<Seat> findByMerchant(Merchant merchant);

    /* 좌석 삭제 쿼리 */
    void deleteBySeatCode(Long seatCode);
}
