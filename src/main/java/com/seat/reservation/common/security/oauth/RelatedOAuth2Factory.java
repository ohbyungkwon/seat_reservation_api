package com.seat.reservation.common.security.oauth;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.security.CustomAuthenticationException;
import com.seat.reservation.common.security.MyUserDetails;
import org.springframework.security.core.Authentication;

import java.util.Map;

public class RelatedOAuth2Factory {

    public static OAuth2UserInfo getOauthUserInfo(String provider, Map<String, Object> attributes) {
        if(provider.equalsIgnoreCase("naver")) {
            return new NaverOAuth2UserInfo(attributes);
        }

        if(provider.equalsIgnoreCase("kakao")) {
            return new KakaoOAuth2UserInfo(attributes);
        }

        throw new CustomAuthenticationException("지원하지 않는 인증 업체입니다.");
    }

//    public static UserDto.search getUserDtoInPrincipal(Authentication authentication) {
//        UserDto.search userDto;
//        boolean isAuthLogin = authentication instanceof UserDto.create;
//        if(isAuthLogin) {
//            userDto = (UserDto.search) authentication.getPrincipal();
//        } else {
//            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
//            User user = userDetails.getUser();
//            user.doAuthLogin();
//            userDto = user.convertDto();
//        }
//
//        return userDto;
//    }
}
