package com.seat.reservation.common.dto;

import com.seat.reservation.common.domain.enums.PaymentCode;
import com.seat.reservation.common.domain.enums.PaymentMethod;
import lombok.*;

public class PayDto {

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class InputPayDto{
        private PaymentMethod paymentMethod;
        private String cardNo;
        private String expireYear;
        private String expireMonth;
        private String password;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class OutputPayDto{
        private String authNo;
        private PaymentCode paymentCode;
    }
}
