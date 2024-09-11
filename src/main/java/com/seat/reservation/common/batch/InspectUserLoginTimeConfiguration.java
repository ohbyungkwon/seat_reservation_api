//package com.seat.reservation.common.batch;
//
//
//import com.seat.reservation.common.domain.User;
//import com.seat.reservation.common.dto.MailDto;
//import com.seat.reservation.common.repository.UserRepository;
//import com.seat.reservation.common.service.MailService;
//import com.seat.reservation.common.service.SmsService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.ExecutionContext;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Slf4j
//@Configuration
//@AllArgsConstructor
//public class InspectUserLoginTimeConfiguration {
//    private static final String JOB_EXE_CONTEXT_KEY = "users";
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final UserRepository userRepository;
//
//    private final MailService mailService;
//
//    public Job setUserLockCodeJob(){
//        return jobBuilderFactory.get("setUserLockCodeJob")
//                .preventRestart()
//                .start(this.setUserLockCode(null))
//                .next(this.sendSms())
//                    .on("FAILED")
//                        .to(this.sendEmail())
//                        .end()
//                .build();
//    }
//
//
//    @JobScope
//    private Step setUserLockCode(@Value("#{jobParameters[restrictLoginTerm]}") String restrictLoginTerm){
//        return stepBuilderFactory.get("setUserLockCode")
//                .tasklet((contribution, chunkContext) -> {
//                    int term = Integer.parseInt(restrictLoginTerm);
//                    LocalDate threeMonthsAgo = LocalDate.now().minusMonths(term);
//                    List<User> users = userRepository.findByLastLoginDateIsBefore(threeMonthsAgo);
//                    users.forEach((user -> {
//                        user.setIsLocked(true);
//                        log.info("changed user lock code {}", user.getUserId());
//                    }));
//
//                    ExecutionContext executionContext = chunkContext.getStepContext()
//                            .getStepExecution().getJobExecution().getExecutionContext();
//                    executionContext.put(JOB_EXE_CONTEXT_KEY, users);
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//
//    @JobScope
//    @SuppressWarnings("unchecked")
//    private Step sendSms(){
//        return stepBuilderFactory.get("sendSms")
//                .tasklet((contribution, chunkContext) -> {
//                    String warnMessage = "[좌석 예약 서비스] 장기간 미사용으로 인한 비활성화 안내\n" +
//                            "사이트 미사용 기간이 3달을 초과하여 비활성화 됩니다.";
//                    ExecutionContext executionContext = chunkContext.getStepContext()
//                            .getStepExecution().getJobExecution().getExecutionContext();
//                    List<User> users = (List<User>) executionContext.get(JOB_EXE_CONTEXT_KEY);
//                    if(users == null || users.size() == 0) {
//                        return RepeatStatus.FINISHED;
//                    }
//
//                    try {
//                        SmsService smsService = new SmsService("tempId", "tempPw");
//                        users.forEach((user -> {
//                            smsService.sendMessage(user.getPhoneNum(), "01012341234", warnMessage);
//                        }));
//                    } catch (Exception e) {
//                        contribution.setExitStatus(ExitStatus.FAILED);
//                        return RepeatStatus.FINISHED;
//                    }
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//
//    @JobScope
//    @SuppressWarnings("unchecked")
//    private Step sendEmail() {
//        return stepBuilderFactory.get("sendEmail")
//                .tasklet((contribution, chunkContext) -> {
//                    ExecutionContext executionContext = chunkContext.getStepContext()
//                            .getStepExecution().getJobExecution().getExecutionContext();
//                    List<User> users = (List<User>) executionContext.get(JOB_EXE_CONTEXT_KEY);
//                    if(users == null || users.size() == 0) {
//                        return RepeatStatus.FINISHED;
//                    }
//
//                    mailService.sendMail(MailDto.builder()
//                            .toArr(users.stream()
//                                    .map(User::getEmail)
//                                    .toArray(String[]::new))
//                            .subject("[좌석 예약 서비스] 장기간 미사용으로 인한 비활성화 안내")
//                            .text("사이트 미사용 기간이 3달을 초과하여 비활성화 됩니다.")
//                            .build());
//                    return RepeatStatus.FINISHED;
//                }).build();
//    }
//}
//
