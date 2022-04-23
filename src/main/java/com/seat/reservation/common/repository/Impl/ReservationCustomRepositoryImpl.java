package com.seat.reservation.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.seat.reservation.common.domain.QReservation.reservation;
import static com.seat.reservation.common.domain.QSeat.seat;
import static com.seat.reservation.common.domain.QMerchant.merchant;

import com.seat.reservation.common.repository.custom.ReservationCustomRepository;
import com.seat.reservation.common.dto.QReservationDto_show;
import com.seat.reservation.common.dto.ReservationDto;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ReservationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 예약 상세보기
     * - 에약 리스트 중 선택하여 자세히 보기
     * @param reservationId
     * @return
     * {@link ReservationItemCustomRepositoryImpl#findItemInReservationItem(Long)}과 함게 사용
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
                            reservation.merchant.merChantTel,
                            reservation.merchant.merChantName,
                            reservation.merchant.address,
                            reservation.merchant.zipCode,
                            reservation.seat.reservationCost
                            )
                )
                .from(reservation)
                .join(reservation.seat, seat).fetchJoin()
                .join(reservation.merchant, merchant).fetchJoin()
                .fetchOne();
    }
}

