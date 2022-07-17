package com.seat.reservation.common.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDetailDto {
    private MerchantDto.show merchantInfo;
    private List<MerchantInfoDto.show> MerchantReservationInfo;
}
