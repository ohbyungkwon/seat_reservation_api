package com.seat.reservation.common.security.oauth;

import com.seat.reservation.common.domain.enums.Gender;
import com.seat.reservation.common.security.CustomAuthenticationException;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    private final Map<String, Object> contentAttrs;
    private final Map<String, Object> propertiesAttrs;

    @SuppressWarnings("unchecked")
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        contentAttrs = (Map<String, Object>) attributes.get("kakao_account");
        propertiesAttrs = (Map<String, Object>) attributes.get("properties");
        if(contentAttrs == null) {
            throw new CustomAuthenticationException("응답 가공에 실패했습니다.");
        }
    }

    @Override
    public String getId() {
        return (String) this.attributes.get("id");
    }

    @Override
    public String getName() {return (String) propertiesAttrs.get("nickname"); }

    @Override
    public String getEmail() {
        return (String) this.contentAttrs.get("email");
    }

    @Override
    public Gender getGender() {
        String gender = (String) this.contentAttrs.get("gender");
        if(gender.equalsIgnoreCase("female")) {
            return Gender.WOMAN;
        }
        return Gender.MAN;
    }

    @Override
    public String getBirth() {
        return null;
    }

    @Override
    public String getPhoneNum() {
        return null;
    }

    @Override
    public int getAge() {
        return 0;
    }
}