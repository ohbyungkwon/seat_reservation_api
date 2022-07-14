package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    Boolean saveReservation(ReservationDto.create dto);

    Boolean removeReservation(Long reservationId);
    Page<ReservationDto.show> selectReservations(SearchDto.date search, Pageable pageable);
    ReservationDetailDto selectReservationDetail(Long reservationId);
}
