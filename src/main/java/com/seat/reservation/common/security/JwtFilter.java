package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Create Bean at {@link com.seat.reservation.common.security.WebSecurityConfig}
 * This bean manages to access services.
 */
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final CustomRedisCache redisCache;
    private final String[] allowUrl;

    public JwtFilter(CustomRedisCache redisCache, String[] allowUrl) {
        this.redisCache = redisCache;
        this.allowUrl = allowUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String url = request.getRequestURI();

            log.info("======= Access JwtFilter =======");
            log.info("addr: {}", request.getRemoteAddr());
            log.info("request url: {}", url);
            log.info("http method: {}", request.getMethod());

            if(!ArrayUtils.contains(allowUrl, url)) {
                String token = TokenUtils.removeTokenType(request.getHeader(AuthConstants.AUTH_HEADER));
                if (StringUtils.isEmpty(token)) {
                    throw new CustomForbiddenException("토큰이 필요합니다.");
                }

                boolean isValid = TokenUtils.isValidToken(token, AuthConstants.ACCESS_TOKEN_TYPE);
                if (!isValid) {
                    throw new CustomForbiddenException("토큰이 만료되었습니다.");
                }

                UserDto.search user = TokenUtils.getUser(token);
                String firstPath = url.split("/")[1];
                if(firstPath.equals("admin")){
                    if(!user.getRole().isAccessAdminPage()){
                        throw new CustomForbiddenException("Admin 사용자가 아닙니다.");
                    }
                }

                String redisTokenKey = AuthConstants.getAccessTokenKey(user.getUserId());
                try {
                    String tokenInRedis = (String) redisCache.getValue(redisTokenKey);
                    if(!token.equals(tokenInRedis)) {
                        throw new CustomForbiddenException("만료된 토근입니다.");
                    }
                } catch (Exception cle) {
                    throw new CustomForbiddenException("사용자 정보를 가져오는데 실패했습니다.");
                }

                // Save an authenticated object at ContextHolder
                Authentication workedAuthentication = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.singleton(user.getRole()));
                SecurityContextHolder.getContext().setAuthentication(workedAuthentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e){
            String msg = "토큰 검증과정에 문제가 발생하였습니다.";
            if(e instanceof CustomForbiddenException){
                msg = e.getMessage();
            }

            Authentication tmpAuthentication = new UsernamePasswordAuthenticationToken(
                    null, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(tmpAuthentication);
            throw new CustomForbiddenException(msg);
        }
    }
}
