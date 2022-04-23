package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;

public class ReservationDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show{
        private int totalPrice;
        private boolean isPreOrder;
        private LocalDateTime reservationDate;
        private String seatCode;

        private String repPhone;
        private String merchantTel;
        private String merchantName;
        private String address;
        private String zipCode;

        private int reservationCost;

        @QueryProjection
        public show(int totalPrice, boolean isPreOrder, LocalDateTime reservationDate,
                    String seatCode, String repPhone, String merchantTel,
                    String merchantName, String address, String zipCode,
                    int reservationCost) {
            this.totalPrice = totalPrice;
            this.isPreOrder = isPreOrder;
            this.reservationDate = reservationDate;
            this.seatCode = seatCode;
            this.repPhone = repPhone;
            this.merchantTel = merchantTel;
            this.merchantName = merchantName;
            this.address = address;
            this.zipCode = zipCode;
            this.reservationCost = reservationCost;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create{
        private Long reservationId;
        private Long seatId;
        private String userId;
        private int totalPrice;
        private boolean isPreOrder;
        private LocalDateTime reservationDate;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class update{
        private Long seatId;
        private LocalDateTime reservationDate;
    }
}
