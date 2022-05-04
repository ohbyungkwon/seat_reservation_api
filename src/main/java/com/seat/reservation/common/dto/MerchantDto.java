package com.seat.reservation.common.dto;

import lombok.Data;

public class MerchantDto {
    @Data
    public static class create{
        private Integer merchantRegNum;
        private String repPhone;
        private String repName;
        private String merchantTel;
        private String merchantName;
        private Long upzongId;
        private String address;
        private String zipCode;
    }
}
