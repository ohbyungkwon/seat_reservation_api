package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.SearchDto;

import java.util.List;

public interface CommonService {
    List<SearchDto.date> getReservationAbleHours(Integer merchantRegNum);
}
