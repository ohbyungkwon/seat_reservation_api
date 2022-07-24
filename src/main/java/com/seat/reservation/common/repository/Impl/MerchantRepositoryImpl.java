package com.seat.reservation.common.repository.Impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.QMerchantDto_show;
import com.seat.reservation.common.repository.custom.MerchantRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.seat.reservation.common.domain.QMerchant.merchant;
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


    public BooleanExpression eqMerchantName(String merchantName){
        return StringUtils.isEmpty(merchantName) ? null : merchant.merchantName.eq(merchantName);
    }
    public BooleanExpression eqMerchantRegNum(Integer merchantRegNum){
        return StringUtils.isEmpty(merchantRegNum) ? null : merchant.merchantRegNum.eq(merchantRegNum);
    }

}

