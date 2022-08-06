package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.*;
import com.seat.reservation.common.repository.MerchantHistoryRepository;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.SeatRepository;
import com.seat.reservation.common.service.HistoryService;
import com.seat.reservation.common.service.MerchantService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor // 생성자 생성을 대신하자
@Transactional(readOnly = true)
public class MerchantServiceImpl extends SecurityService implements HistoryService, MerchantService {
    private final MerchantRepository merchantRepository;
    private final MerchantHistoryRepository merchantHistoryRepository;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public void historySave(Object entity) {
        Merchant merchant = (Merchant) entity;
        Merchant beforeMerchant = merchantRepository.findByMerchantRegNum(merchant.getMerchantRegNum());
        if(beforeMerchant != null) {
            log.info("MERCHANT HISTORY INSERT!!!");
            MerchantHistory merchantHistory = MerchantHistory.builder()
                    .merchant(beforeMerchant)
                    .merchantRegisterCode(beforeMerchant.getRegisterCode())
                    .build();

            merchantHistoryRepository.save(merchantHistory);
        }
    }

    @Override
    public void registerMerchant(Merchant merchant) throws Exception {
    }

    @Override
    public Page<MerchantDto.show> findByMerchantList(MerchantDto.search search, Pageable pageable) {
        return merchantRepository.findMerchantList(search, pageable);
    }


    @Override
    public MerchantDto.showDetail findByMerchantDetail(Integer merchantRegNum) {
        List<MerchantDto.showMerchantWithItem> merchantWithItemList = merchantRepository.findMerchantWithItem(merchantRegNum);
        List<ReviewDto.showSimpleList> reviewList = merchantRepository.findReview(merchantRegNum);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterOneHour = now.plusHours(1);
        List<SeatDto.showByTime> seatList = seatRepository.findSeatByTime(merchantRegNum, now, afterOneHour);

        return MerchantDto.showDetail.builder()
                .merchantWithItemList(merchantWithItemList)
                .reviewList(reviewList)
                .seatList(seatList)
                .build();
    }
}
