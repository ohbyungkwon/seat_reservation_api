package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class SeatDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show{
        private String seatCode; // 좌석 번호

        private int reservationCost; // 좌석 예약 비용
        private boolean isUse; // 사용중

        private int merchantRegNumber; // 사업자 번호

        @QueryProjection
        public show(String seatCode, int reservationCost, boolean isUse, int merchantRegNumber) {
            this.seatCode = seatCode;
            this.reservationCost = reservationCost;
            this.isUse = isUse;
            this.merchantRegNumber = merchantRegNumber;
        }
    }
}
