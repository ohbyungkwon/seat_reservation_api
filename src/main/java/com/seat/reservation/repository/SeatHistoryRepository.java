package com.seat.reservation.repository;

import com.seat.reservation.domain.Seat;
import com.seat.reservation.domain.SeatHistory;
import com.seat.reservation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatHistoryRepository extends JpaRepository<SeatHistory, Long> {

    /* 좌석의 등록, 수정, 삭제 이력을 가져오는 쿼리 */
    List<SeatHistory> findBySeat(Seat seat);

    /* 유저가 등록, 수정, 삭제한 이력을 가져오는 쿼리 */
    List<SeatHistory> findByUser(User user);

    /* 날짜 범위에 따라 이력을 보는 쿼리 */
    List<SeatHistory> findByRegisterDateBetween(LocalDateTime start, LocalDateTime end);
}
