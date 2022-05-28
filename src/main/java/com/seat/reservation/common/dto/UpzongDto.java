package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.enums.Category;
import lombok.*;

public class UpzongDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show{
        private Long id;
        private String code;



        @QueryProjection
        public show(Long id, String code) {
            this.id = id;
            this.code = code;
        }
    }

    @Builder
    @Data
    public static class create{
        private Category category;
        private String code;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class update{
        private Category category;
        private String code;
    }

}
