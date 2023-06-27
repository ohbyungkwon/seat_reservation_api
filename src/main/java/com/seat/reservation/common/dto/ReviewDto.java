package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Review;
import com.seat.reservation.common.domain.enums.Category;
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
        private Long changeFileId;
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


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class showSimple {
        private String title;
        private String comment;
    }
}
