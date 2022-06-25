package com.seat.reservation.common.security.intercept;

import com.seat.reservation.common.security.AuthConstants;
import com.seat.reservation.common.security.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log4j2
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) throws IOException {
        final String header = request.getHeader(AuthConstants.AUTH_HEADER);

        if(header != null){
            final String token = TokenUtils.getTokenFormHeader(header);
            if(TokenUtils.isValidToken(token)){
                return true;
            }
        }
        response.sendRedirect("/error/unauthorized");
        return false;
    }
}
