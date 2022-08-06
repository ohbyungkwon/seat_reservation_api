package com.seat.reservation.common.service;

import com.seat.reservation.common.support.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class SmsService {

    private String coolSmsId; // coolsms id
    private String coolSmsPw; // coolsms pw
    private SMS sms;

    public SmsService(String coolSmsId, String coolSmsPw)
    {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        this.coolSmsId = coolSmsId;
        this.coolSmsPw = coolSmsPw;

        sms = applicationContext.getBean(SMS.class);

        sms.appversion("TEST/1.0");
        sms.charset("utf8");
        sms.setuser(coolSmsId, coolSmsPw);				// coolsms 계정 입력해주시면되요
    }

    public void sendMessage(String receiveNumber
                                 , String sendNumber
                                 , String sendMessage){
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        SmsMessagePdu pdu = applicationContext.getBean(SmsMessagePdu.class);

        pdu.type = "SMS";
        pdu.destinationAddress = receiveNumber;
        pdu.scAddress = sendNumber;                   // 발신자 번호
        pdu.text = sendMessage;					    // 보낼 메세지 내용

        sms.add(pdu);

        try {
            sms.connect();
            sms.send();
            sms.disconnect();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        sms.printr();
        sms.emptyall();
    }
}
