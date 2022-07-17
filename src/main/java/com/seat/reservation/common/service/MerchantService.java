package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.ReservationDetailDto;

public interface MerchantService {
    void registerMerchant(Merchant merchant) throws Exception;

    ReservationDetailDto selectReservationDetail(Long reservationId);
}
