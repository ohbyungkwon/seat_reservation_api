package com.seat.reservation.common.dto;

import lombok.*;

public class MenuDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class search{
        private String menuId;
        private String menuName;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class searchAll{
        private String userId;
        private String menuId;
        private String menuName;
    }
}
