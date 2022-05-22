package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.ReservationItem;
import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ReservationItemDto;
import com.seat.reservation.common.repository.Impl.ReservationRepositoryImpl;
import com.seat.reservation.common.repository.ReservationItemRepository;
import com.seat.reservation.common.repository.ReservationRepository;
import com.seat.reservation.common.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationItemRepository reservationItemRepository;

    /**
     * Reservation 상세 조회
     * @param reservationId
     * @return ReservationDetailDto
     */
    @Override
    public ReservationDetailDto selectReservationDetail(Long reservationId) {
        ReservationDto.show reservation = reservationRepository.findReservationDetail(reservationId)
                .convertReservationDtoShow();
        List<ReservationItemDto.show> reservationItems = reservationItemRepository.findItemInReservationItem(reservationId)
                .stream()
                .map(ReservationItem::convertReservationItemDtoShow)
                .collect(Collectors.toList());

        return ReservationDetailDto.builder()
                .reservationInfo(reservation)
                .reservationItemInfo(reservationItems)
                .build();
    }
}
