package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.Menu;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.ReservationItem;
import com.seat.reservation.common.dto.MerchantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchantRepositoryCustom {

    List<Merchant> findMerchant(Integer merchantRegNum);

    Page<MerchantDto.show> findMerchantList(MerchantDto.search search, Pageable pageable);

    List<Merchant> findMerchantDetail(Integer merchantRegNum);
}
