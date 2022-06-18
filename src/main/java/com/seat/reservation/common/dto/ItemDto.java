package com.seat.reservation.common.dto;

import com.seat.reservation.common.domain.enums.Category;
import lombok.Builder;
import lombok.Data;

public class ItemDto {
    @Data
    @Builder
    public static class create{
        private Integer merchantRegNum;
        private String menuName;
        private int price;
    }

    @Data
    @Builder
    public static class delete{
        private Integer merchantRegNum;
        private String menuName;
    }
}
