package com.seat.reservation.common.batch;


import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class WarnUserLoginTimeConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserRepository userRepository;

    @Bean
    public Job setUserWarnJob(){
        return jobBuilderFactory.get("setUserWarnJob")
                .incrementer(new RunIdIncrementer())
                .start(this.setUseWarnCode())
                .build();
    }

    private Step setUseWarnCode(){
        return stepBuilderFactory.get("setUseWarnCode")
                .tasklet((contribution, chunkContext) -> {

                    String warnMessage = new String("사이트 미사용 기간이 120일을 초과하였습니다." +
                            "30일간 로그인 기록이 없을시 계정이 비활성화됩니다.");

                    /* 추후 coolsms 사이트에 회원 가입 후 id와 pw를 입력해야 함. */
                    SmsService smsService = new SmsService("tempId", "tempPw");

                    List<User> users = userRepository.findUserByLoginTimeBeforeFourMonth();

                    users.forEach((user -> {
                        smsService.sendMessage(user.getPhoneNum(), "01012341234", warnMessage);
                    }));

                    return RepeatStatus.FINISHED;
                }).build();
    }
}
