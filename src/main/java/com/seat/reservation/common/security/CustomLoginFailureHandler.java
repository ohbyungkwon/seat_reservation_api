package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg("로그인에 실패하였습니다.")
                .resultObj(null)
                .build();

        String responseBody = objectMapper.writeValueAsString(responseComDto);
        CommonUtil.writeResponse(response, responseBody);
    }
}
