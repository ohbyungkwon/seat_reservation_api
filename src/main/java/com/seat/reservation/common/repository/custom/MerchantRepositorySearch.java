package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.dto.MerchantDto;

import java.util.List;

public interface MerchantRepositorySearch {

    List<MerchantDto.show> findMerchant(Integer merchantRegNum);

}
