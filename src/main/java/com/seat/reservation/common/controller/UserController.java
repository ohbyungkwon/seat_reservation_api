package com.seat.reservation.common.controller;

import com.seat.reservation.common.domain.enums.SmsOrEmailAuthGoal;
import com.seat.reservation.common.dto.MailDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.service.MailService;
import com.seat.reservation.common.service.UserService;
import com.seat.reservation.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailService mailService;

    /**
     * @param dto
     * @param result
     * @return ResponseEntity<ResponseComDto>
     * @throws Exception
     * - 회원가입
     */
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

    /**
     * @param dto
     * @param result
     * @return ResponseEntity<ResponseComDto>
     * @throws Exception
     * - 회원 정보 수정
     */
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

    /**
     * @param request
     * @param response
     * @return ResponseEntity<ResponseComDto>
     * @throws Exception
     * - 토큰갱신(AccessToken, RefreshToken)
     */
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

    /**
     * @return ResponseEntity<ResponseComDto>
     * - 이메일 인증 전송
     */
    @PostMapping("/auth/email")
    public ResponseEntity<ResponseComDto> sendAuthEmail(@RequestBody MailDto mailDto) throws MessagingException {
        mailService.sendAuthMail(mailDto);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("메일 전송이 완료되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }


    /**
     * @return ResponseEntity<ResponseComDto>
     * - 이메일 인증 검증
     */
    @PostMapping("/check/auth")
    public ResponseEntity<ResponseComDto> checkAuthEmail(
            @RequestParam String authCode,
            @RequestParam String userId,
            @RequestParam SmsOrEmailAuthGoal authGoal) {
        mailService.checkAuthMail(authCode, userId);
        String msg = mailService.doAuthGoal(authGoal, userId);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg(msg)
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }
}
