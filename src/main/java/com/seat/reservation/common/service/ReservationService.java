package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.PayDto;
import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDateTime;

public interface ReservationService {
    Boolean saveReservation(ReservationDto.create dto, PayDto.InputPayDto inputPayDto) throws IOException;

    Boolean removeReservation(Long reservationId, PayDto.InputPayDto inputPayDto);
    Page<ReservationDto.show> selectReservations(SearchDto.date search, Pageable pageable) throws IOException;
    ReservationDetailDto selectReservationDetail(Long reservationId) throws IOException;

    Boolean completeReservation(ReservationDto.update update);
}
