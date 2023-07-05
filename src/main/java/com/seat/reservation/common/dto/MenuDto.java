package com.seat.reservation.common.dto;

import com.seat.reservation.common.domain.enums.Role;
import lombok.*;

public class MenuDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class search{
        private String menuId;
        private String menuName;
        private Role role;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create{
        private String menuId;
        private String menuName;
        private Role role;
    }
}
