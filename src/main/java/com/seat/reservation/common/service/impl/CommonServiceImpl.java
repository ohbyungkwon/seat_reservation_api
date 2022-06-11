package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.SearchDto;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.service.CommonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<SearchDto.date> getReservationAbleHours(Integer merchantRegNum) {
        Merchant merchant = merchantRepository.findByMerchantRegNum(merchantRegNum);
        LocalDateTime openDateTime = merchant.getOpenDateTime();
        LocalDateTime closeDateTime = merchant.getCloseDateTime();
        int stdHour = merchant.getReservationStdHour();

        int cnt = 0;
        int openHour = openDateTime.getHour();
        int closeHour = closeDateTime.getHour();
        List<SearchDto.date> list = new ArrayList<>();
        for(double i = openHour; i <= closeHour - stdHour; i += 0.5) {
            SearchDto.date searchDto = new SearchDto.date();
            int interval = RESERVATION_INTERVAL * cnt++;
            LocalDateTime newDateTime = openDateTime.plusMinutes(interval);
            searchDto.setStartDateTime(newDateTime);
            searchDto.setEndDateTime(newDateTime.plusHours(stdHour));
            list.add(searchDto);
        }

        return list;
    }
}
