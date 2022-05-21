package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.enums.RegisterCode;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


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

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create{
        private Integer merchantRegNum; // 사업자 등록 번호 8자리?

        private String seatCode; // 좌석 번호

        private int reservationCost; // 좌석 예약 비용
        private boolean isUse; // 사용중

        @Enumerated(EnumType.STRING)
        private RegisterCode registerCode; // 등록 코드
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class update{
        private Long id;

        private int reservationCost; // 좌석 예약 비용
        private boolean isUse; // 사용중

        @Enumerated(EnumType.STRING)
        private RegisterCode registerCode; // 등록 코드
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateIsUse{
        private Long id;
        private boolean isUse;
    }
}
