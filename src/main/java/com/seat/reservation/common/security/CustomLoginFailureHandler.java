package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.util.CommonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Create Bean at {@link com.seat.reservation.common.security.WebSecurityConfig}
 * Spring Security access this when login is failed.
 */
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    private final UserRepository userRepository;

    public CustomLoginFailureHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMsg = exception.getMessage();

        String userId = CommonUtil.getStr(request.getAttribute("userId"));
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            int failCnt = user.plusLoginFailCount();
            if(failCnt == 5) {
                errMsg += "(5회 이상 실패하여 계정이 잠금 처리되었습니다)";
                user.setIsLocked(true);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg(errMsg)
                .resultObj(null)
                .build();

        String responseBody = objectMapper.writeValueAsString(responseComDto);
        CommonUtil.writeResponse(response, responseBody);
    }
}
