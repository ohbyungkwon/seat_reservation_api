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
    void registerMerchant(Merchant merchant) throws Exception;
    Page<MerchantDto.show> findByMerchantList(SearchDto.date search, Pageable pageable); // 가맹점 조회
    //List<MerchantDto.show> selectMerchant(MerchantDto.show show); // 가맹점 리스트 가져오기 위해 만든 것 -> 수정 필요
    MerchantDetailDto findByMerchantListDetail(Integer merchantRegNum); // 가맹점 상세조회
}
