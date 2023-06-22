package com.seat.reservation.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
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
import java.util.stream.Collectors;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final String[] allowUrl;
    public JwtFilter(String[] allowUrl) {
        this.allowUrl = allowUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String url = request.getRequestURI();
            log.debug("Url: {}", url);

            if(!ArrayUtils.contains(allowUrl, url)) {
                String token = request.getHeader(AuthConstants.AUTH_HEADER);
                if (StringUtils.isEmpty(token)) {
                    throw new CustomAuthenticationException("토큰이 필요합니다.");
                }

                boolean isValid = TokenUtils.isValidToken(token);
                if (!isValid) {
                    throw new CustomAuthenticationException("토큰이 만료되었습니다.");
                }

                UserDto.create user = TokenUtils.getUser(token);
                Authentication workedAuthentication = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(user.getRole()));
                SecurityContextHolder.getContext().setAuthentication(workedAuthentication);// Session 사용시 설정 필요
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
