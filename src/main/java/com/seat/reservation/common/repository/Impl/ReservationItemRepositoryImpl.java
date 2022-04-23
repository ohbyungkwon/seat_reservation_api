package com.seat.reservation.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import static com.seat.reservation.common.domain.QReservationItem.reservationItem;
import static com.seat.reservation.common.domain.QItem.item;

import com.seat.reservation.common.repository.custom.ReservationItemRepositoryCustom;
import com.seat.reservation.common.dto.QReservationItemDto_show;
import com.seat.reservation.common.dto.ReservationItemDto;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<ReservationItemDto.show> findItemInReservationItem(Long reservationId) {
        return jpaQueryFactory.select(
                    new QReservationItemDto_show(
                            reservationItem.item.menuName,
                            reservationItem.item.price,
                            reservationItem.item.category
                    )
                )
                .from(reservationItem)
                .join(reservationItem.item, item).fetchJoin()
                .where(reservationItem.id.eq(reservationId))
                .fetch();
    }
}

