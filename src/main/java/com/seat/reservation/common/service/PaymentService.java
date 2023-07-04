package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.enums.PaymentMethod;
import com.seat.reservation.common.dto.PayDto;

/**
 * {@link com.seat.reservation.common.service.impl.CardPayServiceImpl}
 * {@link com.seat.reservation.common.service.impl.TossPayServiceImpl}
 * {@link com.seat.reservation.common.service.impl.KakaoPayServiceImpl}
 * {@link com.seat.reservation.common.service.impl.NaverPayServiceImpl}
 * {@link com.seat.reservation.common.service.impl.MoneyPayServiceImpl}
 */
public interface PaymentService {
    PayDto.OutputPayDto pay(Reservation reservation, PayDto.InputPayDto payDto);
    PaymentMethod getPaymentMethod();
}
