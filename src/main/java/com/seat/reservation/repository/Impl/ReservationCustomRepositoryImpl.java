package com.seat.reservation.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.seat.reservation.domain.QReservation.reservation;
import static com.seat.reservation.domain.QSeat.seat;
import static com.seat.reservation.domain.QMerchant.merchant;

import com.seat.reservation.dto.QReservationDto_show;
import com.seat.reservation.dto.ReservationDto;
import com.seat.reservation.repository.custom.ReservationCustomRepository;
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

