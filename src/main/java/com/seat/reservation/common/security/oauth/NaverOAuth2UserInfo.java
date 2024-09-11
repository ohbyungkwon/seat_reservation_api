package com.seat.reservation.common.security.oauth;

import com.seat.reservation.common.domain.enums.Gender;
import com.seat.reservation.common.security.CustomAuthenticationException;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {
    private final Map<String, Object> contentAttrs;

    @SuppressWarnings("unchecked")
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        contentAttrs = (Map<String, Object>) attributes.get("response");
        if(contentAttrs == null) {
            throw new CustomAuthenticationException("응답 가공에 실패했습니다.");
        }
    }

    @Override
    public String getId() {
        return (String) this.contentAttrs.get("id");
    }

    @Override
    public String getName() {
        return (String) this.contentAttrs.get("name");
    }

    @Override
    public String getEmail() {
        return (String) this.contentAttrs.get("email");
    }

    @Override
    public Gender getGender() {
        String gender = (String) this.contentAttrs.get("gender");
        if(gender.equalsIgnoreCase("F")) {
            return Gender.WOMAN;
        }
        return Gender.MAN;
    }

    @Override
    public String getBirth() {
        String birthYear = (String) this.contentAttrs.get("birthyear");
        String birthDay = (String) this.contentAttrs.get("birthday");
        return birthYear + "-" + birthDay;
    }

    @Override
    public String getPhoneNum() {
        return (String) this.contentAttrs.get("mobile");
    }

    @Override
    public int getAge() {
        String ageStr = (String) this.contentAttrs.get("age");
        return Integer.parseInt(ageStr);
    }
}