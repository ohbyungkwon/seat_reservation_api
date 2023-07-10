package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.CacheName;
import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.domain.enums.SmsOrEmailAuthGoal;
import com.seat.reservation.common.dto.MailDto;
import com.seat.reservation.common.dto.properties.MailProperties;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.service.MailService;
import com.seat.reservation.common.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    private final MailProperties mailProperties;

    private final Map<String, CustomRedisCache> redisCacheMap;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    @Override
    @Transactional
    public String doAuthGoal(SmsOrEmailAuthGoal authGoal, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadReqException("사용자를 찾지 못했습니다."));

        if (authGoal.equals(SmsOrEmailAuthGoal.CHANGE_PW)) {
            String randomPw = UUID.randomUUID().toString().substring(0, 6);
            user.changePw(randomPw, passwordEncoder);
            user.setIsNeedChangePw(true);
            return "비밀번호: " + randomPw + "로 변경되었습니다.";
        }

        if (authGoal.equals(SmsOrEmailAuthGoal.CHANGE_ROLE)) {
            user.setRole(Role.NORMAL_ROLE);
            return "권한이 변경되었습니다.";
        }
        return null;
    }

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
        String[] toArr = mailDto.getToArr();
        if(toArr.length != 0) {
            mailDto.setToArr(new String[]{});

            Arrays.sort(toArr); // 정렬
            List<User> users = userRepository.findByEmailInOrderByEmail(toArr);
            if(users.size() != toArr.length)
                throw new BadReqException("부정확한 이메일이 포함되어 있습니다.");

            for(int i = 0; i < users.size(); i++) {
                String userId = users.get(i).getUserId();
                String email = users.get(i).getEmail();
                if(!StringUtils.equals(email, toArr[i])){
                    log.error("[메일전송, {}]-{}, {} 정보가 다릅니다.",
                            i, email, toArr[i]);
                    throw new BadReqException("이메일이 정확하지 않습니다.");
                }

                mailDto.setTo(toArr[i]);
                this.setAuthRandomValue(mailDto, userId);
                this.sendMail(mailDto); // 내용이 모두 다르기 때문에 1건씩 전송
            }
            return;
        }

        User user = userRepository.findByEmail(mailDto.getTo())
                .orElseThrow(() -> new BadReqException("이메일이 정확하지 않습니다."));
        String userId = user.getUserId();
        this.setAuthRandomValue(mailDto, userId);
        this.sendMail(mailDto);
    }

    public void checkAuthMail(String authCode, String userId) {
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

    private void setAuthRandomValue(MailDto mailDto, String userId) {
        String random = UUID.randomUUID().toString().substring(0, 6);
        mailDto.convertAuthFormat(random, userId);
        redisCacheMap.get(CacheName.TOKEN_CACHE.getValue())
                .put(userId + "AUTH_CODE", random);
    }
}
