package com.seat.reservation.common.domain.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum CacheName {
    TOKEN_CACHE("token_cache"),
    MENU_CACHE("menu_cache");

    private String value;

    CacheName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Set<String> getCacheNames(){
        return Arrays.stream(CacheName.values())
                .map(CacheName::getValue)
                .collect(Collectors.toSet());
    }
}
