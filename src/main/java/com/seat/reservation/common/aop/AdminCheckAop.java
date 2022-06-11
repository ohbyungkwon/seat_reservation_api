package com.seat.reservation.common.aop;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.exception.NotAdminException;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.service.SecurityService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class AdminCheckAop extends SecurityService{

    @Pointcut("execution(* com.seat.reservation.admin.controller..*Controller.*(..))")
    private void adminCheck() throws Exception {
         User user = this.getUser().orElseThrow(() ->
                 new NotFoundUserException("사용자를 찾을 수 없습니다."));

         if(user.getRole() != Role.ADMIN_ROLE){
             throw new NotAdminException("Admin 사용자가 아닙니다.");
         }
    }

    private void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        System.out.println(method.getName());

        Object[] args = joinPoint.getArgs();

        for(Object arg:args){
            System.out.println("method : " + arg.getClass().getSimpleName());
            System.out.println("value : " + arg);
        }
    }
}
