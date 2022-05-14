package com.seat.reservation.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.QMerchant;
import com.seat.reservation.common.domain.QSeat;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.QSeatDto_show;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.repository.custom.SeatRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatRepositoryImpl implements SeatRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public SeatRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<SeatDto.show> findSeatInMerchant(int merchantRegNumber) {
        QMerchant merchant = QMerchant.merchant;
        QSeat seat = QSeat.seat;

        return jpaQueryFactory.select(
                    new QSeatDto_show(
                            seat.seatCode,
                            seat.reservationCost,
                            seat.isUse,
                            merchant.merchantRegNumber
                    )
                )
                .from(seat)
                .join(seat.merchant, merchant).fetchJoin()
                .where(merchant.merchantRegNumber.eq(merchantRegNumber)
                        , seat.registerCode.ne(RegisterCode.DELETE))
                .fetch();
    }
}
