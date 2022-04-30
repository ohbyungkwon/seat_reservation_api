package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.SeatHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SeatHistoryRepository extends JpaRepository<SeatHistory, Long> {

    /* 좌석의 등록, 수정, 삭제 이력을 가져오는 쿼리 */
    Page<SeatHistory> findBySeat(Seat seat, Pageable pageable);

    /* 유저가 등록, 수정, 삭제한 이력을 가져오는 쿼리 */
    Page<SeatHistory> findByUser(User user, Pageable pageable);

    /* 날짜 범위에 따라 이력을 보는 쿼리 */
    Page<SeatHistory> findByRegisterDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
