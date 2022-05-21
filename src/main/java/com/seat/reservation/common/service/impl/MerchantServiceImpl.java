package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.MerchantHistoryRepository;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.SeatHistoryRepository;
import com.seat.reservation.common.repository.SeatRepository;
import com.seat.reservation.common.service.HistoryService;
import com.seat.reservation.common.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class MerchantServiceImpl extends SecurityService implements HistoryService {
    private final MerchantRepository merchantRepository;
    private final MerchantHistoryRepository merchantHistoryRepository;

    // 생성자 생성
    public MerchantServiceImpl(MerchantRepository merchantRepository, MerchantHistoryRepository merchantHistoryRepository) {
        this.merchantRepository = merchantRepository;
        this.merchantHistoryRepository = merchantHistoryRepository;
    }


    @Override
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
}
