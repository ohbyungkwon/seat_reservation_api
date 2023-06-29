package com.seat.reservation.admin.service;

import com.seat.reservation.common.dto.SeatDto;

import java.util.List;

public interface SeatAdminService {
    List<SeatDto.show> searchSeatsInMerchant(Integer merchantRegNum);

    void createSeats(List<SeatDto.create> createSeats);

    String updateSeats(SeatDto.update updateSeat);
}
