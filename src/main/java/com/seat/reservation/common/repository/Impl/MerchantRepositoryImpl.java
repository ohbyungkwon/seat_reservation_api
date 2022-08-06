package com.seat.reservation.common.repository.Impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.QMerchantDto_show;
import com.seat.reservation.common.repository.custom.MerchantRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.seat.reservation.common.domain.QItem.item;
import static com.seat.reservation.common.domain.QMenu.menu;
import static com.seat.reservation.common.domain.QMerchant.merchant;
import static com.seat.reservation.common.domain.QReservation.reservation;
import static com.seat.reservation.common.domain.QReservationItem.reservationItem;
import static com.seat.reservation.common.domain.QSeat.seat;
import static com.seat.reservation.common.domain.QUpzong.upzong;

@Repository
public class MerchantRepositoryImpl implements MerchantRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public MerchantRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     *  SELECT *
     *  FROM MERCHANT
     *  WHERE 1 = 1
     *  AND 업종 = 업종
     *  AND 지역 = 지역
     *  AND 상호 = 상호
     */

    // 가맹점 리스트를 가져오는 것
    @Override
    public List<Merchant> findMerchant(Integer merchantRegNum) {
        return jpaQueryFactory.selectFrom(merchant)
                .join(merchant.upzong, upzong).fetchJoin() // 업종 조인
                .where(eqMerchantName(String.valueOf(merchant.merchantName))) // 상호 = 상호
                .fetch();
    }

    @Override
    public Page<MerchantDto.show> findMerchantList(MerchantDto.search search, Pageable pageable) {
        QueryResults<MerchantDto.show> merchants = jpaQueryFactory
                .select(new QMerchantDto_show(
                        merchant.merchantRegNum,
                        merchant.repPhone,
                        merchant.repName,
                        merchant.merchantTel,
                        merchant.merchantName,
                        merchant.address,
                        merchant.zipCode
                ))
                .from(merchant)
                .join(merchant.upzong, upzong).fetchJoin() // 업종 조인
                .where(eqZipCode(search.getZipcode()),
                        eqMerchantName(search.getMerchantName()),
                        eqUpzong(search.getUpzongId()))
                .orderBy(merchant.merchantRegNum.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MerchantDto.show> pageContents = merchants.getResults();
        long total = merchants.getTotal();//result 쿼리 보고 자동 생성
        return new PageImpl<MerchantDto.show>(pageContents, pageable, total);
    }

    @Override
    public List<Merchant> findMerchantDetail(Integer merchantRegNum) {
        return (List<Merchant>) jpaQueryFactory.selectFrom(merchant)
                .join(merchant.upzong, upzong).fetchJoin() // 업종 조인
                .join(seat.merchant, merchant).fetchJoin() // 좌석 조인
                .join(merchant.menu, menu).fetchJoin() // 메뉴 조인
                .where(merchant.merchantRegNum.eq(merchantRegNum)) // 상호 = 상호
                .fetchOne();
    }


    public BooleanExpression eqMerchantName(String merchantName){
        return StringUtils.isEmpty(merchantName) ? null : merchant.merchantName.eq(merchantName);
    }
    public BooleanExpression eqZipCode(String zipcode){
        return StringUtils.isEmpty(zipcode) ? null : merchant.zipCode.eq(zipcode);
    }
    public BooleanExpression eqUpzong(Long upzongId){
        return StringUtils.isEmpty(upzongId) ? null : merchant.upzong.id.eq(upzongId);
    }
    public BooleanExpression eqMerchantRegNum(Integer merchantRegNum){
        return StringUtils.isEmpty(merchantRegNum) ? null : merchant.merchantRegNum.eq(merchantRegNum);
    }

}

