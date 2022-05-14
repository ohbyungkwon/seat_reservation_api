package com.seat.reservation.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.seat.reservation.common.domain.QReservation.reservation;
import static com.seat.reservation.common.domain.QReservationItem.reservationItem;
import static com.seat.reservation.common.domain.QSeat.seat;
import static com.seat.reservation.common.domain.QMerchant.merchant;

import com.seat.reservation.common.repository.custom.ReservationRepositoryCustom;
import com.seat.reservation.common.dto.QReservationDto_show;
import com.seat.reservation.common.dto.ReservationDto;
import org.springframework.stereotype.Repository;

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
    public ReservationDto.show findReservationDetail(Long reservationId) {
        return jpaQueryFactory.select(
                    new QReservationDto_show(
                            reservation.totalPrice,
                            reservation.isPreOrder,
                            reservation.reservationDate,
                            reservation.seat.seatCode,
                            reservation.merchant.repPhone,
                            reservation.merchant.merchantTel,
                            reservation.merchant.merchantName,
                            reservation.merchant.address,
                            reservation.merchant.zipCode,
                            reservation.seat.reservationCost
                            )
                )
                .from(reservation)
                .join(reservation.seat, seat).fetchJoin()
                .join(reservation.merchant, merchant).fetchJoin()
                .where(reservationItem.id.eq(reservationId))
                .fetchOne();
    }
}

