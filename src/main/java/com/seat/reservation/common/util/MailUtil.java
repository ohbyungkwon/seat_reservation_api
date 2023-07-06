package com.seat.reservation.common.util;

import com.seat.reservation.common.dto.MailDto;
import com.seat.reservation.common.dto.properties.MailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.Objects;

@Slf4j
public class MailUtil {

    public static void createMailForm(MimeMessageHelper mimeMessageHelper,
                                      MailProperties mailProperties,
                                      MailDto mailDto) throws MessagingException {
        String from = mailProperties.getUsername();
        String[] toArr = mailDto.getToArr().length == 0 ?
                new String[]{ mailDto.getTo() } : mailDto.getToArr();
        String subject = mailDto.getSubject();
        String text = mailDto.getText();
        MultipartFile[] attachedFiles = mailDto.getMultipartFile();

        log.info("============================");
        log.info("A message's from: {}", from);
        log.info("A message's to: {}", toArr);
        log.info("A message's subject: {}", subject);
        log.info("A message's text: {}", text);
        log.info("A message's attachedFiles: {}", attachedFiles.length);
        log.info("============================");

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(toArr);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        for(MultipartFile file: attachedFiles) {
            mimeMessageHelper.addAttachment(
                    Objects.requireNonNull(file.getOriginalFilename()), file);
        }
    }
}
