package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.domain.enums.RegisterCode;
import lombok.*;

import java.util.List;

public class MerchantDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class search {
        private String merchantName;
        private String zipcode; //현 위치 기반
        private Long upzongId;
        private Boolean isShowCloseMerchant;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show{
        private Integer merchantRegNum;
        private String merchantName;
        private String address;

        private Long reviewCnt;

        @QueryProjection
        public show(Integer merchantRegNum, String merchantName, String address) {
            this.merchantRegNum = merchantRegNum;
            this.merchantName = merchantName;
            this.address = address;
        }

        @QueryProjection
        public show(Integer merchantRegNum, String merchantName, String address, Long reviewCnt) {
            this.merchantRegNum = merchantRegNum;
            this.merchantName = merchantName;
            this.address = address;
            this.reviewCnt = reviewCnt;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class showDetail{
        private showMerchantDetail merchantDetail;
        private List<ReviewDto.showSimple> reviewList;
        private List<SeatDto.showByTime> seatList;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class showMerchantDetail{
        private Integer merchantRegNum;
        private String repPhone;
        private String repName;
        private String merchantTel;
        private String merchantName;
        private String address;
        private String zipCode;

        private Category category;
        private List<ItemDto.show> itemList;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create{
        private Integer merchantRegNum;
        private String repName;
        private String repPhone;
        private String merchantTel;
        private String merchantName;
        private String address;
        private String zipCode;
        private Long upzongId;
        private String openTime;
        private String closeTime;
        private Integer reservationStdHour;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class update{
        private Integer merchantRegNum;
        private String repName;
        private String repPhone;
        private String merchantTel;
        private String merchantName;
        private String address;
        private String zipCode;
        private Long upzongId;
        private String openTime;
        private String closeTime;
        private Integer reservationStdHour;
        private RegisterCode registerCode;
    }
}
