package com.seat.reservation.common.security.oauth;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.security.WebSecurityConfig;
import com.seat.reservation.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Create Bean at {@link WebSecurityConfig}
 * Spring Security access this when login is failed.
 */
@Slf4j
public class CustomOAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    private final String oauthLoginFailureCallbackUrl;

    public CustomOAuth2LoginFailureHandler(String oauthLoginFailureCallbackUrl) {
        this.oauthLoginFailureCallbackUrl = oauthLoginFailureCallbackUrl;
    }

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMsg = exception.getMessage();
        log.info("========= Access CustomOAuth2LoginFailureHandler =========");
        log.info("error message: {}", errMsg);
        log.info("exception class: {}", exception.getClass().getName());
        log.info("localized message: {}", exception.getLocalizedMessage());

        Map<String, Object> resBody = new HashMap<>();
        resBody.put("redirectUrl", oauthLoginFailureCallbackUrl);
        resBody.put("errMsg", errMsg);

        CommonUtil.redirectResponse(response, resBody);
    }
}
