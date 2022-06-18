package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.SearchDto;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.service.CommonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    private static final Integer RESERVATION_INTERVAL = 30;
    private final MerchantRepository merchantRepository;

    public CommonServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<SearchDto.time> getReservationAbleHours(Integer merchantRegNum) {
        Merchant merchant = merchantRepository.findByMerchantRegNum(merchantRegNum);
        LocalTime openTime = merchant.getOpenTime();
        LocalTime closeTime = merchant.getCloseTime();
        int stdHour = merchant.getReservationStdHour();

        int cnt = 0;
        int openHour = openTime.getHour();
        int closeHour = closeTime.getHour();
        List<SearchDto.time> list = new ArrayList<>();
        for(double i = openHour; i <= closeHour - stdHour; i += 0.5) {
            SearchDto.time searchDto = new SearchDto.time();
            int interval = RESERVATION_INTERVAL * cnt++;
            LocalTime newDateTime = openTime.plusMinutes(interval);
            searchDto.setStartTime(newDateTime);
            searchDto.setEndTime(newDateTime.plusHours(stdHour));
            list.add(searchDto);
        }

        return list;
    }
}
