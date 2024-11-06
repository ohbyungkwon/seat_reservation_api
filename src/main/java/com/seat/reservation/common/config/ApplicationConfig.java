package com.seat.reservation.common.config;

import com.seat.reservation.common.dto.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig implements WebMvcConfigurer {

    private final MailProperties mailProperties;

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(30);
        threadPoolTaskExecutor.setCorePoolSize(20);
        return threadPoolTaskExecutor;
    }

    @Bean
    public StringHttpMessageConverter StringHttpMessageConverter() {
        return new StringHttpMessageConverter(
                StandardCharsets.UTF_8
        );
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost(mailProperties.getHost());
//        javaMailSender.setPort(mailProperties.getPort());
//        javaMailSender.setUsername(mailProperties.getUsername());
//        javaMailSender.setPassword(mailProperties.getPassword());
//        javaMailSender.setProtocol(mailProperties.getProtocol());
//
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.auth", mailProperties.getMailSmtpAuth());
//        properties.setProperty("mail.smtp.starttls.enable",
//                mailProperties.getMailSmtpStarttlsEnable());
//        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
