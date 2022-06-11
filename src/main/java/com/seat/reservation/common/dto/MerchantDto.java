package com.seat.reservation.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.Upzong;
import lombok.*;

import java.time.LocalDateTime;

public class MerchantDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class show{
        private Integer merchantRegNum;
        private String repPhone;
        private String repName;
        private String merchantTel;
        private String merchantName;
        private Long upzongId;
        private String address;
        private String zipCode;

        @QueryProjection
        public show(Integer merchantRegNum, String repPhone, String repName,
                    String merchantTel, String merchantName, Long upzongId,
                    String address, String zipCode
                    ) {
            this.merchantRegNum = merchantRegNum;
            this.repName = repName;
            this.repPhone = repPhone;
            this.merchantTel = merchantTel;
            this.merchantName = merchantName;
            this.upzongId = upzongId;
            this.address = address;
            this.zipCode = zipCode;
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
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class update{
        private Integer merchantRegNum;
    }

}
