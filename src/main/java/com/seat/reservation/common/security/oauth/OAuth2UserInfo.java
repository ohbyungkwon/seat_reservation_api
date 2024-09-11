package com.seat.reservation.common.security.oauth;

import com.seat.reservation.common.domain.enums.Gender;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected final Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract Gender getGender();
    public abstract String getBirth();
    public abstract String getPhoneNum();
    public abstract int getAge();
}