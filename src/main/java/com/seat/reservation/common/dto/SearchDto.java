package com.seat.reservation.common.dto;

import lombok.*;

import java.time.LocalDateTime;

public class SearchDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class date{
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
    }
}
