package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.enums.PaymentCode;
import com.seat.reservation.common.domain.enums.PaymentMethod;
import com.seat.reservation.common.dto.PayDto;
import com.seat.reservation.common.service.PaymentService;
import lombok.Getter;

@Getter
public class MoneyPayServiceImpl implements PaymentService {

    private final PaymentMethod paymentMethod = PaymentMethod.MONEY;

    @Override
    public PayDto.OutputPayDto pay(Reservation reservation, PayDto.InputPayDto payDto) {
        /*
         * 현금 결제 로직 추가
         */

        System.out.println("Execute Money Pay");

        return PayDto.OutputPayDto.builder()
                .paymentCode(PaymentCode.APPROVE)
                .authNo("A1234567") // 임시 승인 번호
                .build();
    }
}
