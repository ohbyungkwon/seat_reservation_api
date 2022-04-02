package com.seat.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(30);
        threadPoolTaskExecutor.setCorePoolSize(20);
        return threadPoolTaskExecutor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("**") //CORS 적용할 URL 패턴
                .allowedOrigins("*") //자원 공유 오리진 지정
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS") //요청 허용 메서드
                .allowedHeaders("*") //요청 허용 헤더
                .allowCredentials(true) //요청 허용 쿠키
                .maxAge(MAX_AGE_SECS);
    }
}
