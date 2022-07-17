package com.seat.reservation.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;
import com.seat.reservation.common.domain.Upzong;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.domain.enums.Role;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
