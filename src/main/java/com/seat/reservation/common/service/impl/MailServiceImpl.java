package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.domain.enums.CacheName;
import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.dto.MailDto;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.dto.properties.MailProperties;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.service.MailService;
import com.seat.reservation.common.service.SecurityService;
import com.seat.reservation.common.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl extends SecurityService implements MailService {
    private final JavaMailSender javaMailSender;

    private final MailProperties mailProperties;

    private final Map<String, CustomRedisCache> redisCacheMap;


    @Override
    public void sendMail(MailDto mailDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        MailUtil.createMailForm(mimeMessageHelper, mailProperties, mailDto);

        javaMailSender.send(message);
    }

    @Override
    public void sendAuthMail(MailDto mailDto) throws MessagingException {
        UserDto.create user = this.getUserInfo();
        String userId = user.getUserId();
        Role role = user.getRole();
        if(Role.SYSTEM_ROLE.equals(role)) {
            // system 유저 권한으로 여러명에거 전송 가능
            String[] toArr = mailDto.getToArr();
            mailDto.setToArr(new String[]{});
            for (String to : toArr) {
                mailDto.setTo(to);
                this.setAuthRandomValue(mailDto, userId);
                this.sendMail(mailDto); // 내용이 모두 다르기 때문에 1건씩 전송
            }
            return;
        }

        // system 유저 외 본인만 가능
        String loginEmail = user.getEmail();
        if(StringUtils.equals(loginEmail, mailDto.getTo())) {
            throw new BadReqException("계정의 이메일 정보와 다릅니다.");
        }

        this.setAuthRandomValue(mailDto, userId);
        this.sendMail(mailDto);
    }

    public void checkAuthMail(String authCode) throws MessagingException {
        UserDto.create user = this.getUserInfo();
        String userId = user.getUserId();

        Cache.ValueWrapper wrapper =
                redisCacheMap.get(CacheName.TOKEN_CACHE.getValue())
                        .get(userId + "AUTH_CODE");
        if(wrapper == null) {
            throw new BadReqException("처음부터 진행 부탁드립니다.");
        }

        String code = String.valueOf(wrapper.get());
        if(!StringUtils.equals(code, authCode)) {
            throw new BadReqException("인증코드가 옳바르지 않습니다.");
        }
    }

    private void setAuthRandomValue(MailDto mailDto, String userId)
            throws MessagingException {
        String random = UUID.randomUUID().toString().substring(0, 6);
        mailDto.convertAuthFormat(random, userId);
        redisCacheMap.get(CacheName.TOKEN_CACHE.getValue())
                .put(userId + "AUTH_CODE", random);
    }
}
