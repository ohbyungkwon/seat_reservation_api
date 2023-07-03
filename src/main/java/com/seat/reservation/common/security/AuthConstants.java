package com.seat.reservation.common.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class AuthConstants {
    public static final String AUTH_HEADER = "Authorization";
    public static final String ACCESS_TOKEN_TYPE = "BEARER";

    public static final String ACCESS_TOKEN_KEY_PART = "access-token";

    public static final String REFRESH_TOKEN_KEY_PART = "refresh-token";

    public static final String SEPARATOR = ".";

    public static String getAccessTokenKey(String userId) {
        return userId + SEPARATOR + ACCESS_TOKEN_KEY_PART;
    }

    public static String getRefreshTokenKey() {
        return REFRESH_TOKEN_KEY_PART;
    }
}
