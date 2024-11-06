package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Create Bean at {@link com.seat.reservation.common.security.WebSecurityConfig}
 * Spring Security access this when login is failed.
 */
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    private final UserRepository userRepository;

    public CustomLoginFailureHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMsg = exception.getMessage();
        log.info("========= Access CustomLoginFailureHandler =========");
        log.info("error message: {}", errMsg);
        log.info("exception class: {}", exception.getClass().getName());
        log.info("localized message: {}", exception.getLocalizedMessage());

        String userId = CommonUtil.getStr(request.getAttribute("userId"));
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            int failCnt = user.plusLoginFailCount();
            if(failCnt == 5) {
                errMsg += "\n(5회 이상 실패하여 계정이 잠금 처리되었습니다.\n" +
                        "비밀번호 찾기를 이용해 주세요.)";
                user.setLocked(true);
            }
        }

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseStatus exceptionStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        if(exceptionStatus != null) {
            status = exceptionStatus.value();
        }
        response.setStatus(status.value());

        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg(errMsg)
                .resultObj(null)
                .build();
        CommonUtil.writeResponse(response, responseComDto);
    }
}
