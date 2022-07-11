package com.seat.reservation.common.dto;

import lombok.*;

import java.util.List;

public class ReviewDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class create{
        private String title;
        private String comment;
        private Integer merchantRegNum;
        private Long parentId;
        private Long reservationId;
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class update{
        private Long reviewId;
        private String title;
        private String comment;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class show{
        private String title;
        private String comment;
        private List<ReviewDto.show> subReviews;
    }
}
