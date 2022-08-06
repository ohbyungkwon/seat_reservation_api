package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.repository.custom.SeatRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryCustom {


    /* 좌석 삭제 쿼리 */
    void deleteBySeatCode(String seatCode);

}
