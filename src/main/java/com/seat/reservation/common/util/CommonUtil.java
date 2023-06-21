package com.seat.reservation.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class CommonUtil {

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

    public static void writeResponse(HttpServletResponse response, String body) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.write(body);
        printWriter.flush();
    }
}
