package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MerchantInfoDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show {
        private String merchantName;
        private Long upzongId;
        private String address;

        @QueryProjection
        public show(String merchantName, Long upzongId, String address) {
            this.merchantName = merchantName;
            this.upzongId = upzongId;
            this.address = address;
        }
    }
}