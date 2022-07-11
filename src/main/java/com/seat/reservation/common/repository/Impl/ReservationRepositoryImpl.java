package com.seat.reservation.common.repository.Impl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.repository.custom.ReservationRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.seat.reservation.common.domain.QMerchant.merchant;
import static com.seat.reservation.common.domain.QReservation.reservation;
import static com.seat.reservation.common.domain.QSeat.seat;
import static com.seat.reservation.common.domain.QUser.user;

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
     * @return Reservation
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

    /**
     *
     * @param userId
     * @param start
     * @param end
     * @param pageable
     * @return Page<Reservation>
     */
    @Override
    public Page<Reservation> findByUserAndRegisterDateBetween(String userId, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        QueryResults<Reservation> reservationList = jpaQueryFactory
                .selectFrom(reservation)
                .join(reservation.user, user).fetchJoin()
                .join(reservation.merchant, merchant).fetchJoin()
                .where(
                        reservation.user.userid.eq(userId),
                        reservation.registerDate.between(start, end)
                )
                .orderBy(reservation.registerDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Reservation> pageContents = reservationList.getResults();
        long total = reservationList.getTotal();
        return new PageImpl<Reservation>(pageContents, pageable, total);
    }
}

