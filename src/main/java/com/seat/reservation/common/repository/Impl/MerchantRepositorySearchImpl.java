package com.seat.reservation.common.repository.Impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.QMerchantDto_show;
import com.seat.reservation.common.repository.custom.MerchantRepositorySearch;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.seat.reservation.common.domain.QMerchant.merchant;
import static com.seat.reservation.common.domain.QUpzong.upzong;

@Repository
    public class MerchantRepositorySearchImpl implements MerchantRepositorySearch {
    private final JPAQueryFactory jpaQueryFactory;

    public MerchantRepositorySearchImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     *  SELECT *
     *  FROM MERCHANT
     *  WHERE 1 = 1
     *  AND 업종 = 업종
     *  AND 지역 = 지역
     *  AND 날짜 = 날짜
     *  AND 인원 = 인원
     */

    @Override
    public List<MerchantDto.show> findMerchant(Integer merchantRegNum) {
        return jpaQueryFactory.select(
                    new QMerchantDto_show(
                            merchant.merchantRegNum,
                            merchant.repPhone,
                            merchant.repName,
                            merchant.merchantTel,
                            merchant.merchantName,
                            merchant.upzong.id,
                            merchant.address,
                            merchant.zipCode
                            )
                )
                .from(merchant)
                .join(merchant.upzong, upzong).fetchJoin()
                .where(eqMerchantRegNum(merchantRegNum))
                .fetch();
    }

    public BooleanExpression eqMerchantRegNum(Integer merchantRegNum){
        return StringUtils.isEmpty(merchantRegNum) ? null : merchant.merchantRegNum.eq(merchantRegNum);
    }

}

