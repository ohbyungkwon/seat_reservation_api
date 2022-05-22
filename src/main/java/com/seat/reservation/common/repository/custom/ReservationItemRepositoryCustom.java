package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.ReservationItem;
import java.util.List;

public interface ReservationItemRepositoryCustom {
    List<ReservationItem> findItemInReservationItem(Long reservationId);
}
