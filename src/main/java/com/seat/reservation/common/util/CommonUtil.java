package com.seat.reservation.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seat.reservation.common.dto.ResponseComDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CommonUtil {

    public static final String[] allowUrls = {
        "/login", // 첫번째 항목 수정X
        "/signUp",
        "/renew/token",
        "/auth/email",
        "/oauth2/authorize",
        "/oauth2/authorization",
        "/oauth2/code/naver",
        "/oauth2/code/kakao"
    };

    /**
     * domain, dto 필드명이 모두 일치할 경우만 사용
     */
    public static <T, R> void convertDto(T domain, R dto) {
        BeanUtils.copyProperties(domain, dto);
    }

    public static String getFirstError(BindingResult result) {
        if(result.hasErrors())
          return result.getAllErrors().get(0).getDefaultMessage();
        return "";
    }

    public static LocalDate getNowDate() {
        return LocalDate.now();
    }

    public static LocalDateTime getNowDateTime() {
        return LocalDateTime.now();
    }

    public static Date convertDate(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static void writeResponse(HttpServletResponse response, ResponseComDto body) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String resBodyStr = objectMapper.writeValueAsString(body);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.write(resBodyStr);
        printWriter.flush();
    }

    public static void redirectResponse(HttpServletResponse response, Map<String, Object> resBodyMap) throws IOException {
        String redirectUrl = (String) resBodyMap.get("redirectUrl");
        resBodyMap.remove(redirectUrl);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(redirectUrl);
        for(Map.Entry<String, Object> entry : resBodyMap.entrySet()) {
            uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue().toString());
        }
        UriComponents uriComponents = uriComponentsBuilder.build();
        response.sendRedirect(uriComponents.toUriString());
    }

    public static byte[] convertByte(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        return bos.toByteArray();
    }

    public static Object convertObj(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput oin = new ObjectInputStream(bis);
        return oin.readObject();
    }

    public static String getStr(Object obj) {
        if (!(obj instanceof String)) {
            return "";
        }

        return String.valueOf(Optional.of(obj).orElseGet(() -> ""));
    }

    public static void defaultExceptionHandler(HttpServletResponse response, HttpStatus status, Exception e) throws IOException {
        response.setStatus(status.value());

        String msg = e.getMessage();
        ResponseComDto responseComDto = ResponseComDto.builder()
                .resultMsg(msg)
                .resultObj(null)
                .build();

        writeResponse(response, responseComDto);
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
