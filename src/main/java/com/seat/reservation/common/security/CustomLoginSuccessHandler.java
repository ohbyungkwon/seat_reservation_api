package com.seat.reservation.common.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDto.create user = (UserDto.create) authentication.getPrincipal();
        String token = TokenUtils.generateJwtToken(user);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg("로그인하였습니다.")
                .resultObj(user)
                .build();
        String responseBody = objectMapper.writeValueAsString(responseComDto);
        CommonUtil.writeResponse(response, responseBody);
    }
}
