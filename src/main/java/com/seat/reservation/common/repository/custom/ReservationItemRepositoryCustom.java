package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.dto.ReservationItemDto;

import java.util.List;

public interface ReservationItemRepositoryCustom {
    List<ReservationItemDto.show> findItemInReservationItem(Long reservationId);
}
