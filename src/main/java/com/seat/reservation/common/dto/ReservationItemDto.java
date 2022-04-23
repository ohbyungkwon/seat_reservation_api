package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.enums.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReservationItemDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show{
        private String menuName;
        private int price;
        private Category category;

        @QueryProjection
        public show(String menuName, int price, Category category) {
            this.menuName = menuName;
            this.price = price;
            this.category = category;
        }
    }
}
