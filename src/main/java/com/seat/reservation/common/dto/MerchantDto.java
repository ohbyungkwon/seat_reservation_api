package com.seat.reservation.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Review;
import com.seat.reservation.common.domain.Upzong;
import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.domain.enums.MerchantSortMethod;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.domain.enums.Role;
import lombok.*;
import org.springframework.data.domain.Pageable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        //private MerchantSortMethod sortMethod; 아직 미구현
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show{
        private String merchantName;
        private String address;

        @QueryProjection
        public show(String merchantName, String address) {
            this.merchantName = merchantName;
            this.address = address;
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
