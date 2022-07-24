package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.dto.MerchantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchantRepositoryCustom {

    List<Merchant> findMerchant(Integer merchantRegNum);

    // Page<Reservation> findByUserAndRegisterDateBetween(Integer merchantRegNum, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
