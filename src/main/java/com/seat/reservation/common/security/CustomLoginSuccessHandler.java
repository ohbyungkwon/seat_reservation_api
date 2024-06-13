package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.domain.RefreshTokenStore;
import com.seat.reservation.common.domain.enums.CacheName;
import com.seat.reservation.common.dto.MenuDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.repository.RefreshTokenStoreRepository;
import com.seat.reservation.common.util.CommonUtil;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Create Bean at {@link com.seat.reservation.common.security.WebSecurityConfig}
 * Spring Security access this when login is successful.
 * This bean creates access token and refresh token for the next access.
 */
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Map<String, CustomRedisCache> redisCacheMap;
    private final RefreshTokenStoreRepository refreshTokenStoreRepository;

    public CustomLoginSuccessHandler(Map<String, CustomRedisCache> redisCacheMap,
                     RefreshTokenStoreRepository refreshTokenStoreRepository) {
        this.redisCacheMap = redisCacheMap;
        this.refreshTokenStoreRepository = refreshTokenStoreRepository;
    }

    @Override
    @Transactional
    @SuppressWarnings({"unchecked"})
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDto.create user = (UserDto.create) authentication.getPrincipal();
        String userId = user.getUserId();
        String accessToken = TokenUtils.generateJwtToken(user);
        String redisTokenKey = AuthConstants.getAccessTokenKey(userId);
        redisCacheMap.get(CacheName.TOKEN_CACHE.getValue()).put(redisTokenKey, accessToken);
        response.addHeader(AuthConstants.AUTH_HEADER,
                AuthConstants.ACCESS_TOKEN_TYPE + " " + accessToken);

        RefreshTokenStore refreshToken;
        Optional<RefreshTokenStore> savedRefreshToken = refreshTokenStoreRepository.findById(userId);
        if(savedRefreshToken.isPresent()) {
            refreshToken = savedRefreshToken.get();
            boolean isValid = TokenUtils.isValidToken(refreshToken.getRefreshToken(), "");
            if(!isValid) {
                refreshToken.renewRefreshTokenStore();
            }
        } else {
            refreshToken = RefreshTokenStore.createRefreshTokenStore(userId);
            refreshTokenStoreRepository.save(refreshToken);
        }

        String cookieValue = refreshToken.getCookieValue();
        Cookie cookie = new Cookie(AuthConstants.getRefreshTokenKey(), cookieValue);
        response.addCookie(cookie);

        String key = user.getRole().getValue() + "_menu_list";
        Cache.ValueWrapper wrapper = redisCacheMap.get(CacheName.MENU_CACHE.getValue()).get(key);
        List<MenuDto.search> menus = (wrapper == null ? null : (List<MenuDto.search>) wrapper.get());

        Map<String, Object> resBody = new HashMap<>();
        resBody.put("user", user);
        resBody.put("menus", menus);

        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg("로그인하였습니다.")
                .resultObj(resBody)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(responseComDto);
        CommonUtil.writeResponse(response, responseBody);
    }
}
