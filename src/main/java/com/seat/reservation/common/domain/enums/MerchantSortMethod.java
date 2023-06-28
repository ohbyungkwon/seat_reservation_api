package com.seat.reservation.common.domain.enums;

public enum MerchantSortMethod {
    START("star"),
    REVIEW("review");

    private String sortMethod;

    MerchantSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }
}
