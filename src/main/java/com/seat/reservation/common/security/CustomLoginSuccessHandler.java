package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final CustomRedisCacheWriter customRedisCacheWriter;

    public CustomLoginSuccessHandler(CustomRedisCacheWriter customRedisCacheWriter) {
        this.customRedisCacheWriter = customRedisCacheWriter;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDto.create user = (UserDto.create) authentication.getPrincipal();
        String token = TokenUtils.generateJwtToken(user);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);

        String redisTokenKey = AuthConstants.getAccessTokenKey(user.getUserId());
        customRedisCacheWriter.put(redisTokenKey, token);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg("로그인하였습니다.")
                .resultObj(user)
                .build();
        String responseBody = objectMapper.writeValueAsString(responseComDto);
        CommonUtil.writeResponse(response, responseBody);
    }
}
