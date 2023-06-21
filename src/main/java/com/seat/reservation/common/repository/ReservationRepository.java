package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.repository.custom.ReservationRepositoryCustom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    Page<Reservation> findByUser(User user, Pageable pageable);

    Boolean existsByIdAndUser(Long reservationId, User user);

    @Query(value =  "SELECT DATE_FORMAT(R.REGISTSER_DATE, '%y%m%d') YYMMDD" +
                    "     , DATE_FORMAT(R.REGISTER_DATE, '%H') HH" +
                    "     , R.MERCHANT_ID" +
                    "     , R.SEAT_ID" +
                    "     , SUM(R.TOTAL_PRICE)" +
                    "  FROM RESERVATION R" +
                    " WHERE DATE_FORMAT(R.REGISTER_DATE, '%y%m%d') = :findDate" +
                    " GROUP BY 1,2,3,4"
            , nativeQuery = true)
    List<Object[]> calculateSumOfReservationByDate(@Param("findDate") String findDate);
}
