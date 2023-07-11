package com.seat.reservation.common.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@Profile("!local")
public class BatchConfig implements BatchConfigurer {
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public BatchConfig(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private MapJobRepositoryFactoryBean getJobRepositoryFactoryBean() throws Exception {
        MapJobRepositoryFactoryBean jobRepositoryFactoryBean =
                new MapJobRepositoryFactoryBean(this.transactionManager);
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean;
    }

    @Override
    public JobRepository getJobRepository() throws Exception {
        return this.getJobRepositoryFactoryBean().getObject();
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(this.getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        MapJobExplorerFactoryBean jobExplorerFactoryBean =
                new MapJobExplorerFactoryBean(this.getJobRepositoryFactoryBean());
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }
}
