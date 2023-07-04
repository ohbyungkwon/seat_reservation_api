package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.MerchantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * {@link com.seat.reservation.common.service.impl.MerchantServiceImpl}
 */
public interface MerchantService {
    void registerMerchant(MerchantDto.create merchantDto) throws Exception;

    void updateMerchant(MerchantDto.update merchantDto) throws Exception;

    Page<MerchantDto.show> findByMerchantList(MerchantDto.search search, Pageable pageable); // 가맹점 조회
    MerchantDto.showDetail findByMerchantDetail(Integer merchantRegNum);
}
