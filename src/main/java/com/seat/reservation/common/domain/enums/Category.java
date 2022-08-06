package com.seat.reservation.common.domain.enums;

public enum Category {
    PC("PC"),
    CAFE("CAFE"),
    HOTEL("HOTEL"),
    RESTAURANT("RESTAURANT");

    public String name;

    public String getName() {
        return name;
    }

    Category(String name) {
        this.name = name;
    }
}
