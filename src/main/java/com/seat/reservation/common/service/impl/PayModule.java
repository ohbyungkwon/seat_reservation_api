package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.PaymentHistory;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.PayDto;
import com.seat.reservation.common.service.PaymentService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PayModule {
    private PaymentService paymentService;

    private PaymentHistory pay(Reservation reservation
            , User user
            , Merchant merchant
            , PayDto.InputPayDto payDto){
        PayDto.OutputPayDto paymentResult = paymentService.pay(reservation, payDto);

        return PaymentHistory.builder()
                .paymentMethod(paymentService.getPaymentMethod())
                .paymentCode(paymentResult.getPaymentCode())
                .authno(paymentResult.getAuthNo())
                .cardNum(payDto.getCardNo())
                .user(user)
                .merchant(merchant)
                .reservation(reservation)
                .totalPrice(reservation.getTotalPrice())
                .build();
    }
}
