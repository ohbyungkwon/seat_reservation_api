package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.MerchantDto;

import java.util.List;

public interface MerchantRepositoryCustom {

    List<Merchant> findMerchant(Integer merchantRegNum);

}
