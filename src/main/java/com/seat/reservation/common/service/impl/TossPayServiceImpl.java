package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.enums.PaymentCode;
import com.seat.reservation.common.domain.enums.PaymentMethod;
import com.seat.reservation.common.dto.PayDto;
import com.seat.reservation.common.service.PaymentService;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TossPayServiceImpl implements PaymentService {

    private final PaymentMethod paymentMethod = PaymentMethod.TOSS;

    @Override
    public PayDto.OutputPayDto pay(Reservation reservation, PayDto.InputPayDto payDto) {
        /*
         * 토스페이 로직 추가
         */

        System.out.println("Execute Toss Pay");

        return PayDto.OutputPayDto.builder()
                .paymentCode(PaymentCode.APPROVE)
                .authNo("A1234567") // 임시 승인 번호
                .build();
    }
}
