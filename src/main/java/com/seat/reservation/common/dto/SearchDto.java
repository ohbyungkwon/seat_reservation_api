package com.seat.reservation.common.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class SearchDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class time{
        private LocalTime startTime;
        private LocalTime endTime;
    }

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
