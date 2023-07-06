package com.seat.reservation.common.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String to; // 단건(다른 내용으로 전달)
    private String[] toArr; // 다중(같은 내용으로 여러명)

    private String subject;
    private String text;
    private MultipartFile[] multipartFile;

    public void convertAuthFormat(String random, String userId){
        this.subject = "[좌석예약, 이메일 인증] 이미일을 인증해주세요.";
        this.text = userId + "님, 안녕하세요." +
                "\n어플로 돌아가 아래 인증번호를 입력해주세요." +
                "\n인증번호: " + random;
    }
}
