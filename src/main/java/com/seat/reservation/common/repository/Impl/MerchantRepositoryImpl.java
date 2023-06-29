package com.seat.reservation.common.repository.Impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.*;
import com.seat.reservation.common.repository.custom.MerchantRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.util.List;

import static com.seat.reservation.common.domain.QItem.item;
import static com.seat.reservation.common.domain.QMerchant.merchant;
import static com.seat.reservation.common.domain.QUpzong.upzong;
import static com.seat.reservation.common.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class MerchantRepositoryImpl implements MerchantRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

//    @Override
//    public Page<MerchantDto.show> findMerchantList(MerchantDto.search search, Pageable pageable) {
//        QueryResults<MerchantDto.show> merchants = jpaQueryFactory
//                .select(
//                        new QMerchantDto_show(
//                                merchant.merchantRegNum,
//                                merchant.merchantName,
//                                merchant.address
//                        )
//                )
//                .from(merchant)
//                .join(merchant.upzong, upzong) // 업종 조인
//                .where(eqZipCode(search.getZipcode()),
//                        eqMerchantName(search.getMerchantName()),
//                        eqUpzong(search.getUpzongId()),
//                        isShowCloseMerchant(search.getIsShowCloseMerchant()))
//                .orderBy(merchant.review.size().desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//
//        List<MerchantDto.show> pageContents = merchants.getResults();
//        long total = merchants.getTotal();//result 쿼리 보고 자동 생성
//        return new PageImpl<MerchantDto.show>(pageContents, pageable, total);
//    }

    @Override
    public Page<MerchantDto.show> findMerchantList(MerchantDto.search search, Pageable pageable) {
        StringPath reviewCntAlias = Expressions.stringPath("reviewCnt");
        List<MerchantDto.show> pageContents = jpaQueryFactory
                .select(
                        new QMerchantDto_show(
                                merchant.merchantRegNum,
                                merchant.merchantName,
                                merchant.address,
                                review.id.count().as("reviewCnt")
                        )
                )
                .from(merchant)
                .join(merchant.upzong, upzong)
                .leftJoin(merchant.review, review)
                .where(eqZipCode(search.getZipcode()),
                        eqMerchantName(search.getMerchantName()),
                        eqUpzong(search.getUpzongId()),
                        isShowCloseMerchant(search.getIsShowCloseMerchant()))
                .groupBy(merchant.merchantName, merchant.address)
                .orderBy(reviewCntAlias.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(merchant.merchantRegNum.count())
                .from(merchant)
                .where(eqZipCode(search.getZipcode()),
                        eqMerchantName(search.getMerchantName()),
                        eqUpzong(search.getUpzongId()),
                        isShowCloseMerchant(search.getIsShowCloseMerchant()))
                .fetchCount(); // 업종은 필수 등록값이기 때문에 조인 제외

        return new PageImpl<MerchantDto.show>(pageContents, pageable, total);
    }

    @Override
    public Merchant findMerchantDetail(Integer merchantRegNum) {
        return jpaQueryFactory
                .selectDistinct(merchant)
                .from(merchant)
                .join(merchant.upzong, upzong)
                .join(merchant.item, item).fetchJoin()
                .where(eqMerchantRegNum(merchantRegNum))
                .fetchOne();
    }

    public BooleanExpression isShowCloseMerchant(boolean isShowCloseMerchant){
        LocalTime now = LocalTime.now();
        return isShowCloseMerchant ? null : merchant.closeTime.after(now);
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
        return StringUtils.isEmpty(merchantRegNum) ? null : item.merchant.merchantRegNum.eq(merchantRegNum);
    }

}

