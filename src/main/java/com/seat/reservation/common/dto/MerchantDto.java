package com.seat.reservation.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Upzong;
import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.domain.enums.Role;
import lombok.*;

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
        private Long upzongId;
        private String merchantName;
        private String zipcode;
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
        private List<showMerchantWithItem> merchantWithItemList;
        private List<ReviewDto.showSimpleList> reviewList;
        private List<SeatDto.showByTime> seatList;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    public static class showMerchantWithItem{
        private Integer merchantRegNum;
        private String repPhone;
        private String repName;
        private String merchantTel;
        private String merchantName;
        private Category upzongCategory;
        private String address;
        private String zipCode;
        private List<ItemDto.show> itemList;

        @QueryProjection
        public showMerchantWithItem(Integer merchantRegNum, String repPhone, String repName,
                    String merchantTel, String merchantName, Category upzongCategory,
                    String address, String zipCode, List<Item> itemList) {
            this.merchantRegNum = merchantRegNum;
            this.repName = repName;
            this.repPhone = repPhone;
            this.merchantTel = merchantTel;
            this.merchantName = merchantName;
            this.upzongCategory = upzongCategory;
            this.address = address;
            this.zipCode = zipCode;

            List<ItemDto.show> temp = new ArrayList<>();
            for (Item item : itemList) {
                temp.add(ItemDto.show.builder()
                        .menuName(item.getMenuName())
                        .price(item.getPrice())
                        .build());
            }
            this.itemList = temp;
        }
    }






    @Builder
    @Data
    public static class create{
        private Integer merchantRegNum;
        private String repPhone;
        private String repName;
        private String merchantTel;
        private String merchantName;
        private Upzong upzongId;
        private String address;
        private String zipCode;

        @Enumerated(EnumType.STRING)
        private Role role;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class update{
        private Integer merchantRegNum;

        @Enumerated(EnumType.STRING)
        private RegisterCode registerCode; // 등록 코드
    }



}
