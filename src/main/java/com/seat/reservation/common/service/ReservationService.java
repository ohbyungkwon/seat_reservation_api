package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    Boolean saveReservation(ReservationDto.create dto);

    Page<ReservationDto.show> selectReservations(ReservationDto.search search, Pageable pageable);
    ReservationDetailDto selectReservationDetail(Long reservationId);
}
