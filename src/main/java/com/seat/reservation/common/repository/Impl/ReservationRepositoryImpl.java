package com.seat.reservation.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.repository.custom.ReservationRepositoryCustom;
import org.springframework.stereotype.Repository;

import static com.seat.reservation.common.domain.QMerchant.merchant;
import static com.seat.reservation.common.domain.QReservation.reservation;
import static com.seat.reservation.common.domain.QReservationItem.reservationItem;
import static com.seat.reservation.common.domain.QSeat.seat;

@Repository
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ReservationRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 예약 상세보기
     * - 에약 리스트 중 선택하여 자세히 보기
     * @param reservationId
     * @return
     * {@link ReservationItemRepositoryImpl#findItemInReservationItem(Long)}과 함게 사용
     */
    @Override
    public Reservation findReservationDetail(Long reservationId) {
        return jpaQueryFactory.selectFrom(reservation)
                .join(reservation.seat, seat).fetchJoin()
                .join(reservation.merchant, merchant).fetchJoin()
                .where(reservation.id.eq(reservationId))
                .fetchOne();
    }
}

