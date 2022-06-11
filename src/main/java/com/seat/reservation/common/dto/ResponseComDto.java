package com.seat.reservation.common.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseComDto {
    private String resultCode;
    private String resultMsg;
    private Object resultObj;
}
