package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import com.seat.reservation.common.domain.RefreshTokenStore;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.repository.RefreshTokenStoreRepository;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final CustomRedisCache redisCache;
    private final RefreshTokenStoreRepository refreshTokenStoreRepository;

    public CustomLoginSuccessHandler(CustomRedisCache redisCache,
                     RefreshTokenStoreRepository refreshTokenStoreRepository) {
        this.redisCache = redisCache;
        this.refreshTokenStoreRepository = refreshTokenStoreRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDto.create user = (UserDto.create) authentication.getPrincipal();
        String userId = user.getUserId();
        String accessToken = TokenUtils.generateJwtToken(user);
        String redisTokenKey = AuthConstants.getAccessTokenKey(userId);
        redisCache.put(redisTokenKey, accessToken);
        response.addHeader(AuthConstants.AUTH_HEADER,
                AuthConstants.ACCESS_TOKEN_TYPE + " " + accessToken);

        RefreshTokenStore refreshToken = RefreshTokenStore.createRefreshTokenStore(userId);
        String cookieValue = refreshToken.getCookieValue();
        Cookie cookie = new Cookie(AuthConstants.getRefreshTokenKey(), cookieValue);
        response.addCookie(cookie);
        refreshTokenStoreRepository.save(refreshToken);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg("로그인하였습니다.")
                .resultObj(user)
                .build();
        String responseBody = objectMapper.writeValueAsString(responseComDto);
        CommonUtil.writeResponse(response, responseBody);
    }
}
