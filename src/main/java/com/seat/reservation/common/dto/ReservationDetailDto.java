package com.seat.reservation.common.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetailDto {
    private ReservationDto.show reservationInfo;
    private List<ReservationItemDto.show> reservationItemInfo;
}
