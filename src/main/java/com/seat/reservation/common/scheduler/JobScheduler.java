//package com.seat.reservation.common.scheduler;
//
//
//import com.seat.reservation.common.batch.CalculateReservationSumByDateConfiguration;
//import com.seat.reservation.common.batch.InspectUserLoginTimeConfiguration;
//import com.seat.reservation.common.batch.WarnUserLoginTimeConfiguration;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.JobParameter;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class JobScheduler {
//
//    private final JobLauncher jobLauncher;
//    private final CalculateReservationSumByDateConfiguration calculateReservationSumByDateConfiguration;
//    private final InspectUserLoginTimeConfiguration inspectUserLoginTimeConfiguration;
//    private final WarnUserLoginTimeConfiguration warnUserLoginTimeConfiguration;
//
//    /* INFO (cron = "분 시 일 월 요일")
//    * report 를 위한 job schedule
//    * 매일 00시 10분에 수행
//    * */
//    @Scheduled(cron = "10 00 * * *")
//    public void setCalculateReservationSumByDateConfigurationJob(){
//        Map<String, JobParameter> confMap = new HashMap<>();
//        confMap.put("time", new JobParameter(System.currentTimeMillis()));
//        JobParameters jobParameters = new JobParameters(confMap);
//
//        try{
//            jobLauncher.run(calculateReservationSumByDateConfiguration.groupByReservationJob()
//                            , jobParameters);
//        }catch (Exception e){
//               log.error(e.getMessage());
//        }
//    }
//
//    /* INFO (cron = "분 시 일 월 요일")
//     * 장기 미접속자에게 경고 문자를 보내는 schedule
//     * 매일 15시 00분에 수행
//     * */
//    @Scheduled(cron = "00 15 * * *")
//    public void setWarnUserLoginTimeConfigurationJob(){
//        Map<String, JobParameter> confMap = new HashMap<>();
//        confMap.put("time", new JobParameter(System.currentTimeMillis()));
//        JobParameters jobParameters = new JobParameters(confMap);
//
//        try{
//            jobLauncher.run(warnUserLoginTimeConfiguration.setUserWarnJob()
//                    , jobParameters);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//    }
//
//    /* INFO (cron = "분 시 일 월 요일")
//     * 장기 미접속자에게 경고 문자를 보내는 schedule
//     * 매일 15시 00분에 수행
//     * */
//    @Scheduled(cron = "00 01 * * *")
//    public void setInspectUserLoginTimeConfigurationJob(){
//        Map<String, JobParameter> confMap = new HashMap<>();
//        confMap.put("time", new JobParameter(System.currentTimeMillis()));
//        JobParameters jobParameters = new JobParameters(confMap);
//
//        try{
//            jobLauncher.run(inspectUserLoginTimeConfiguration.setUserLockCodeJob()
//                    , jobParameters);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//    }
//}
