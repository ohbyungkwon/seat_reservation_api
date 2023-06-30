package com.seat.reservation.common.repository.Impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.QMerchant;
import com.seat.reservation.common.domain.QReservation;
import com.seat.reservation.common.domain.QSeat;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.QSeatDto_show;
import com.seat.reservation.common.dto.QSeatDto_showByTime;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.repository.custom.SeatRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SeatRepositoryImpl implements SeatRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public SeatRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<SeatDto.show> findSeatInMerchant(int merchantRegNum) {
        QMerchant merchant = QMerchant.merchant;
        QSeat seat = QSeat.seat;

        return jpaQueryFactory.select(
                    new QSeatDto_show(
                            seat.seatCode,
                            seat.reservationCost,
                            seat.merchant.merchantRegNum
                    )
                )
                .from(seat)
                .join(seat.merchant, merchant)
                .where(merchant.merchantRegNum.eq(merchantRegNum)
                        , seat.registerCode.ne(RegisterCode.DELETE))
                .fetch();
    }

    @Override
    public List<SeatDto.showByTime> findSeatByTime(int merchantRegNum, LocalDateTime startTime) {
        QSeat seat = QSeat.seat;
        QReservation reservation = QReservation.reservation;

        return jpaQueryFactory.select(
                        new QSeatDto_showByTime(
                                seat.seatCode,
                                new CaseBuilder()
                                        .when(seat.isUse.eq(Boolean.FALSE)).then(
                                                (Predicate) new CaseBuilder()
                                                .when(reservation.id.isNull()).then(false)// 예약 여부 확인
                                                .when(reservation.reservationEndDateTime.before(startTime)).then(false)// 예약 시간 확인
                                                .otherwise(true)
                                        )
                                        .otherwise(true)
                                        .as("isUse")
                        )
                ).from(reservation)
                .rightJoin(reservation.seat, seat)
                .where(seat.merchant.merchantRegNum.eq(merchantRegNum))
                .fetch();
    }

//    public List<SeatDto.showByTime> findSeatByTime(int merchantRegNum, LocalDateTime startTime, LocalDateTime endTime) {
//        QSeat seat = QSeat.seat;
//        QReservation reservation = QReservation.reservation;
//
//        return jpaQueryFactory.select(
//                    new QSeatDto_showByTime(
//                            seat.seatCode,
//                            new CaseBuilder()
//                                    .when(seat.isUse.eq(Boolean.FALSE).and(reservation.id.isNull())).then(false)
//                                    .otherwise(true)
//                                    .as("isUse")
//                    )
//                ).from(reservation)
//                .rightJoin(reservation.seat, seat)
//                .on(seat.merchant.merchantRegNum.eq(merchantRegNum),
//                        reservation.reservationEndDateTime.between(startTime, endTime)
//                )
//                .fetch();
//    }
}
