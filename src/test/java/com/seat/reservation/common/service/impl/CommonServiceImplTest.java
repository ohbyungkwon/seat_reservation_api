package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.SearchDto;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.service.CommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonServiceImplTest {
    @Autowired
    private CommonService commonService;

    @Test
    @Rollback(value = false)
    public void getReservationAbleHours() {
        List<SearchDto.time> timeList = commonService.getReservationAbleHours(1);
        timeList.forEach(x -> {
            System.out.println(x.getStartTime() + " ~ " + x.getEndTime());
        });
    }
}