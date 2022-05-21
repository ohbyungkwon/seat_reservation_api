package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Merchant;

public interface MerchantService {
    void registerMerchant(Merchant merchant) throws Exception;
}
