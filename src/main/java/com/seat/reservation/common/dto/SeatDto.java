package com.seat.reservation.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
    @ToString
    public static class show{
        private String seatCode; // 좌석 번호

        private int reservationCost; // 좌석 예약 비용

        private int merchantRegNumber; // 사업자 번호

        @QueryProjection
        public show(String seatCode, int reservationCost, int merchantRegNumber) {
            this.seatCode = seatCode;
            this.reservationCost = reservationCost;
            this.merchantRegNumber = merchantRegNumber;
        }
    }
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @ToString
    public static class showByTime{
        private String seatCode;
        private boolean isUse;

        @QueryProjection
        public showByTime(String seatCode, boolean isUse){
            this.seatCode = seatCode;
            this.isUse = isUse;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class create{
        private Integer merchantRegNum; // 사업자 등록 번호 8자리?

        private String seatCode; // 좌석 번호

        private int reservationCost; // 좌석 예약 비용

        @Enumerated(EnumType.STRING)
        private RegisterCode registerCode; // 등록 코드
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class update{
        private Long id;

        private int reservationCost; // 좌석 예약 비용

        @Enumerated(EnumType.STRING)
        private RegisterCode registerCode; // 등록 코드
    }
}
