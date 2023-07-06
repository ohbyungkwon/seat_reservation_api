package com.seat.reservation;

import com.seat.reservation.common.dto.properties.MailProperties;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
@EnableConfigurationProperties(MailProperties.class)
public class ReservationApiApplication {

	/*
	* 1. /Users/macbook/.gradle/caches/modules-2/files-2.1/org.springframework.batch/spring-batch-core/4.1.0.RELEASE/588a97c9a6a190b4db9a9757f888da59c613021f/spring-batch-core-4.1.0.RELEASE.jar!/org/springframework/batch/core/schema-mysql.sql
	*    에 있는 스크립트 DB에 실행
	* */
	public static void main(String[] args) {
		SpringApplication.run(ReservationApiApplication.class, args);
	}

}
