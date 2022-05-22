package com.seat.reservation.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.ReservationItem;
import com.seat.reservation.common.repository.custom.ReservationItemRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.seat.reservation.common.domain.QItem.item;
import static com.seat.reservation.common.domain.QReservationItem.reservationItem;

@Repository
public class ReservationItemRepositoryImpl implements ReservationItemRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ReservationItemRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }


    /**
     * ReservationItem 내부 Item 리스트 반환
     * - 예약 메뉴 보기 기능
     * @param reservationId
     * @return
     * {@link ReservationRepositoryImpl#findReservationDetail(Long)}과 함게 사용
     */
    @Override
    public List<ReservationItem> findItemInReservationItem(Long reservationId) {
        return jpaQueryFactory.selectFrom(reservationItem)
                .join(reservationItem.item, item).fetchJoin()
                .where(reservationItem.id.eq(reservationId))
                .fetch();
    }
}

