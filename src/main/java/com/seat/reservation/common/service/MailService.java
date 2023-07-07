package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.enums.SmsOrEmailAuthGoal;
import com.seat.reservation.common.dto.MailDto;

import javax.mail.MessagingException;

public interface MailService {
    void sendMail(MailDto mailDto) throws MessagingException;

    void sendAuthMail(MailDto mailDto) throws MessagingException;

    void checkAuthMail(String authCode) throws MessagingException;

    String doAuthGoal(SmsOrEmailAuthGoal authGoal);
}
