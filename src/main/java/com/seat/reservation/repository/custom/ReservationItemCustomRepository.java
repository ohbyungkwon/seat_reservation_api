package com.seat.reservation.repository.custom;

import com.seat.reservation.dto.ReservationItemDto;

import java.util.List;

public interface ReservationItemCustomRepository {
    List<ReservationItemDto.show> findItemInReservationItem(Long reservationId);
}
