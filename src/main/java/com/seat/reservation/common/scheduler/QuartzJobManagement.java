package com.seat.reservation.common.scheduler;

import com.seat.reservation.common.scheduler.quartz.SendMailToLockUser;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@EnableBatchProcessing
public class QuartzJobManagement {
    @Value("${spring.profiles.active}")
    private String profiles;

    private final Scheduler scheduler;

    @Autowired
    public QuartzJobManagement(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void start() {
        if(profiles.equals("prod")) {
            JobDetail sendEmailToLockUser = this.buildJobDetail(
                    SendMailToLockUser.class, "SendMailToLockUser", "tasklet");

            try {
                scheduler.scheduleJob(sendEmailToLockUser, buildJobTrigger("0 0 1 * * ?"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    private <T extends Job> JobDetail buildJobDetail(Class<T> job, String name, String group) {
        return JobBuilder.newJob(job).withIdentity(name, group).build();
    }

}
