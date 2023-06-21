//package com.seat.reservation.common.batch;
//
//
//import com.seat.reservation.common.domain.User;
//import com.seat.reservation.common.repository.UserRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//@AllArgsConstructor
//@Slf4j
//public class InspectUserLoginTimeConfiguration {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final UserRepository userRepository;
//
//    @Bean
//    public Job setUserLockCodeJob(){
//        return jobBuilderFactory.get("setUserLockCodeJob")
//                .incrementer(new RunIdIncrementer())
//                .start(this.setUserLockCode())
//                .build();
//    }
//
//    private Step setUserLockCode(){
//        return stepBuilderFactory.get("setUserLockCode")
//                .tasklet((contribution, chunkContext) -> {
//
//                    List<User> users = userRepository.findUserByLoginTimeBeforeThreeMonth();
//
//                    users.forEach((user -> {
//                        user.setIsLocked(true);
//
//                        log.info("changed user lock code {}", user.getUserid());
//
//                        userRepository.save(user);
//                    }));
//
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//}
