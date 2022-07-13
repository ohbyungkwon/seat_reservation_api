package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.enums.PaymentMethod;
import com.seat.reservation.common.dto.PayDto;

public interface PaymentService {
    PayDto.OutputPayDto pay(Reservation reservation, PayDto.InputPayDto payDto);
    PaymentMethod getPaymentMethod();
}
