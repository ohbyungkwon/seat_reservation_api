package com.seat.reservation.common.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create Bean at {@link com.seat.reservation.common.security.WebSecurityConfig}
 * This bean is accessed to 'login' at first.
 * It is return unauthenticated object
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws org.springframework.security.core.AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest;
        try{
            Map<String, String> map = mapper.readValue(request.getReader().lines().collect(Collectors
                            .joining()), new TypeReference<Map<String, String>>(){});

            String userId = map.get("userId");
            String password = map.get("password");

            request.setAttribute("userId", userId);
            authRequest = new UsernamePasswordAuthenticationToken(userId, password);
        } catch (IOException exception){
            String msg = "입력 형식을 확인해주세요.";
            throw new CustomAuthenticationException(msg);
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
