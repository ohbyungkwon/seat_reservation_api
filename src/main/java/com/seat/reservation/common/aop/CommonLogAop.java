package com.seat.reservation.common.aop;

import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.service.SecurityService;
import com.seat.reservation.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class CommonLogAop extends SecurityService {

    @Pointcut("execution(* com.seat.reservation.*.controller..*Controller.*(..))")
    private void controller() {}

    @Around("controller()")
    private Object controllerAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if(ArrayUtils.contains(CommonUtil.allowUrls, request.getRequestURI())) {
            return printRunTimeLog(joinPoint, CommonUtil.getClientIpAddr(request));
        } else {
            UserDto.search userDto = this.getUserInfo();
            return printCommonLog(joinPoint, userDto);
        }
    }

    private Object printCommonLog(ProceedingJoinPoint joinPoint, UserDto.search userDto) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("{} USER, ACCESS METHOD: {}", userDto.getUserId(), method);

        boolean isSysUser = userDto.getRole().equals(Role.SYSTEM_ROLE);
        if (isSysUser) {
            return this.printSysUserLog(joinPoint, userDto.getUserId());
        } else {
            return this.printRunTimeLog(joinPoint, userDto.getUserId());
        }
    }

    private Object printSysUserLog(ProceedingJoinPoint joinPoint, String userId) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            for (Field field : arg.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                log.info("ACCESS METHOD ARGS#{}, {}", field.getName(), field.get(arg));
            }
        }

        Object result = printRunTimeLog(joinPoint, userId);
        log.info("ACCESS METHOD RESULT: {}", result);
        return result;
    }

    private Object printRunTimeLog(ProceedingJoinPoint joinPoint, String userId) throws Throwable {
        LocalDateTime startTime = LocalDateTime.now();
        Object result = joinPoint.proceed();
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        log.info("{} USER, METHOD RUNTIME: {}초", userId, duration.getSeconds());
        return result;
    }
}
