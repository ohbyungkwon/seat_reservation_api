package com.seat.reservation.common.controller;

import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.security.AuthConstants;
import com.seat.reservation.common.service.UserService;
import com.seat.reservation.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

/* 회원 등록, 수정, 탈퇴 (조회 기능은 Spring Security에서 자동으로 함) */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseComDto> createUser(
            @Valid @RequestBody UserDto.create dto, BindingResult result) throws Exception {
        String errMsg = CommonUtil.getFirstError(result);
        if(!StringUtils.isEmpty(errMsg)) {
            throw new BadReqException(errMsg);
        }

        UserDto.create createdUser = userService.createUser(dto);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("회원가입 완료되었습니다.")
                        .resultObj(createdUser)
                        .build(), HttpStatus.OK);
    }


    @PutMapping("/modify/user")
    public ResponseEntity<ResponseComDto> updateUser(
            @Valid @RequestBody UserDto.update dto, BindingResult result) throws Exception {
        String errMsg = CommonUtil.getFirstError(result);
        if(!StringUtils.isEmpty(errMsg)) {
            throw new BadReqException(errMsg);
        }

        userService.updateUser(dto);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("회원 정보가 수정되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/renew/token")
    public ResponseEntity<ResponseComDto> renewToken(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        userService.manageRefreshToken(request, response);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("토큰이 정상적으로 갱신되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/auth/email")
    public ResponseEntity<ResponseComDto> authEmail() {
        // TODO) 이메일 인증 작업 후 임시 토큰 발급
        return null;
    }
}
