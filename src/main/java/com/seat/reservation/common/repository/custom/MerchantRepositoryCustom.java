package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchantRepositoryCustom {

    List<Merchant> findMerchant(Integer merchantRegNum);

    Page<MerchantDto.show> findMerchantList(MerchantDto.search search, Pageable pageable);

    Merchant findMerchantDetail(Integer merchantRegNum);
}
