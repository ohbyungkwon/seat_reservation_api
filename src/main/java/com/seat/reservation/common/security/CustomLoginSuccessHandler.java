package com.seat.reservation.common.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.domain.RefreshTokenStore;
import com.seat.reservation.common.domain.enums.CacheName;
import com.seat.reservation.common.dto.MenuDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.repository.RefreshTokenStoreRepository;
import com.seat.reservation.common.security.oauth.RelatedOAuth2Factory;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.cache.Cache;
import org.springframework.security.core.Authentication;
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

    protected final Map<String, CustomRedisCache> redisCacheMap;
    protected final RefreshTokenStoreRepository refreshTokenStoreRepository;

    public CustomLoginSuccessHandler(Map<String, CustomRedisCache> redisCacheMap,
                     RefreshTokenStoreRepository refreshTokenStoreRepository) {
        this.redisCacheMap = redisCacheMap;
        this.refreshTokenStoreRepository = refreshTokenStoreRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserDto.search userDto = (UserDto.search) authentication.getPrincipal();

        String accessToken = this.generateAccessToken(userDto);
        response.addHeader(AuthConstants.AUTH_HEADER,
                AuthConstants.ACCESS_TOKEN_TYPE + " " + accessToken);

        String userId = userDto.getUserId();
        RefreshTokenStore refreshToken = this.generateRefreshToken(userId);
        String cookieValue = refreshToken.getCookieValue();
        Cookie cookie = new Cookie(AuthConstants.getRefreshTokenKey(), cookieValue);
        response.addCookie(cookie);

//        - OAuth2와 형식 통일을 위한 주석(기타 정보는 부여받은 토큰으로 API 통하여 요청)
//        String key = user.getRole().getValue() + "_menu_list";
//        Cache.ValueWrapper wrapper = redisCacheMap.get(CacheName.MENU_CACHE.getValue()).get(key);
//        List<MenuDto.search> menus = (wrapper == null ? null : (List<MenuDto.search>) wrapper.get());

//        Map<String, Object> resBody = new HashMap<>();
//        resBody.put("user", user);
//        resBody.put("menus", menus);
//        resBody.put("redirectUrl", oauthLoginSuccessCallbackUrl);

        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg("로그인하였습니다.")
                .resultObj(null)
                .build();

        CommonUtil.writeResponse(response, responseComDto);
    }

    protected RefreshTokenStore generateRefreshToken(String userId) throws JsonProcessingException {
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
        return refreshToken;
    }

    protected String generateAccessToken(UserDto.search userDto) throws JsonProcessingException {
        String userId = userDto.getUserId();
        String accessToken = TokenUtils.generateJwtToken(userDto);
        String redisTokenKey = AuthConstants.getAccessTokenKey(userId);
        redisCacheMap.get(CacheName.TOKEN_CACHE.getValue()).put(redisTokenKey, accessToken);

        return accessToken;
    }
}
