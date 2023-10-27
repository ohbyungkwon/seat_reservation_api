package com.seat.reservation.common.scheduler.quartz;

import com.seat.reservation.common.support.ApplicationContextProvider;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Profile("!local")
@RequiredArgsConstructor
public class SendMailToLockUser extends QuartzJobBean {
    private final JobLauncher jobLauncher;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobParametersBuilder jobParam = new JobParametersBuilder();
        jobParam.addString("date", new Date().toString());
        jobParam.addString("restrictLoginTerm", "3");

        Job job = ApplicationContextProvider
                .getApplicationContext()
                .getBean("setUserLockCodeJob", Job.class);

        try {
            JobExecution je = jobLauncher.run(job, jobParam.toJobParameters());
            context.setResult(je.getId());
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                 JobInstanceAlreadyCompleteException | JobRestartException e) {
            throw new RuntimeException(e);
        }
    }
}
