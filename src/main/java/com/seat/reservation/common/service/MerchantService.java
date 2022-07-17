package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.dto.MerchantDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MerchantService {
    void registerMerchant(Merchant merchant) throws Exception;
    Page<ReservationDto.show> selectReservations(SearchDto.date search, Pageable pageable);
    MerchantDetailDto selectReservationDetail(Long reservationId);
}
