package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.MerchantDetailDto;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MerchantService {
    void registerMerchant(MerchantDto.create merchantDto) throws Exception;

    void updateMerchant(MerchantDto.update merchantDto) throws Exception;

    Page<MerchantDto.show> findByMerchantList(MerchantDto.search search, Pageable pageable); // 가맹점 조회
    MerchantDto.showDetail findByMerchantDetail(Integer merchantRegNum);
}
