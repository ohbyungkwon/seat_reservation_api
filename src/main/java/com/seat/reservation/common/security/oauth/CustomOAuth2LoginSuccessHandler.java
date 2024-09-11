package com.seat.reservation.common.security.oauth;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.domain.RefreshTokenStore;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.CacheName;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.repository.RefreshTokenStoreRepository;
import com.seat.reservation.common.security.*;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Create Bean at {@link WebSecurityConfig}
 * Spring Security access this when login is successful.
 * This bean creates access token and refresh token for the next access.
 */
public class CustomOAuth2LoginSuccessHandler extends CustomLoginSuccessHandler {

    private final String oauthLoginSuccessCallbackUrl;

    public CustomOAuth2LoginSuccessHandler(Map<String, CustomRedisCache> redisCacheMap,
                                           RefreshTokenStoreRepository refreshTokenStoreRepository,
                                           String oauthLoginSuccessCallbackUrl) {
        super(redisCacheMap, refreshTokenStoreRepository);
        this.oauthLoginSuccessCallbackUrl = oauthLoginSuccessCallbackUrl;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        user.doAuthLogin();

        UserDto.search userDto = user.convertDto();
        String accessToken = this.generateAccessToken(userDto);

        String userId = userDto.getUserId();
        RefreshTokenStore refreshToken = this.generateRefreshToken(userId);

        Map<String, Object> resBody = new HashMap<>();
        resBody.put("redirectUrl", oauthLoginSuccessCallbackUrl);
        resBody.put(AuthConstants.getRefreshTokenKey(), refreshToken.getCookieValue());
        resBody.put(AuthConstants.getAccessTokenKey(), accessToken);

        CommonUtil.redirectResponse(response, resBody);
    }
}
