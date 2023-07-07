package com.seat.reservation.common.domain.enums;

public enum SmsOrEmailAuthGoal {
    CHANGE_PW("change_password"),
    CHANGE_ROLE("change_role");

    private String value;

    SmsOrEmailAuthGoal(String value) {
        this.value = value;
    }
}
