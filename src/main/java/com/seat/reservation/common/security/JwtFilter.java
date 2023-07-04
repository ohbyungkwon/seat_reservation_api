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
            log.debug("Url: {}", url);

            if(!ArrayUtils.contains(allowUrl, url)) {
                String token = TokenUtils.removeTokenType(request.getHeader(AuthConstants.AUTH_HEADER));
                if (StringUtils.isEmpty(token)) {
                    throw new CustomAuthenticationException("토큰이 필요합니다.");
                }

                boolean isValid = TokenUtils.isValidToken(token, AuthConstants.ACCESS_TOKEN_TYPE);
                if (!isValid) {
                    throw new CustomAuthenticationException("토큰이 만료되었습니다.");
                }

                UserDto.create user = TokenUtils.getUser(token);
                String firstPath = url.split("/")[1];
                if(firstPath.equals("admin")){
                    if(!user.getRole().isAccessAdminPage()){
                        throw new CustomAuthenticationException("Admin 사용자가 아닙니다.");
                    }
                }

                String redisTokenKey = AuthConstants.getAccessTokenKey(user.getUserId());
                try {
                    String tokenInRedis = (String) redisCache.getValue(redisTokenKey);
                    if(!token.equals(tokenInRedis)) {
                        throw new CustomAuthenticationException("만료된 토근입니다.");
                    }
                } catch (Exception cle) {
                    throw new CustomAuthenticationException("사용자 정보를 가져오는데 실패했습니다.");
                }

                // Save an authenticated object at ContextHolder
                Authentication workedAuthentication = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.singleton(user.getRole()));
                SecurityContextHolder.getContext().setAuthentication(workedAuthentication);
            }

            filterChain.doFilter(request, response);
        } catch (AuthenticationException e){
            response.setStatus(HttpStatus.FORBIDDEN.value());

            String msg = e.getMessage();
            ResponseComDto responseComDto = ResponseComDto.builder()
                    .resultMsg(msg)
                    .resultObj(null)
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(responseComDto);
            CommonUtil.writeResponse(response, body);
        }
    }
}
