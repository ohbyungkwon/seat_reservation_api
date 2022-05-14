package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.enums.Category;
import lombok.*;

public class ReservationItemDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show {
        private String menuName;
        private int price;

        @QueryProjection
        public show(String menuName, int price) {
            this.menuName = menuName;
            this.price = price;
        }
    }
}