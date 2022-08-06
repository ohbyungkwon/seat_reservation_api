package com.seat.reservation.common.batch;


import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.ReportReservation;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.exception.NotFoundInputException;
import com.seat.reservation.common.repository.*;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class CalculateReservationSumByDateConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ReservationRepository reservationRepository;
    private final ReportReservationRepository reportReservationRepository;
    private final SeatRepository seatRepository;
    private final MerchantRepository merchantRepository;

    @Bean
    public Job groupByReservationJob(){
        return jobBuilderFactory.get("groupByReservationJob")
                .incrementer(new RunIdIncrementer())
                .start(this.groupByReservationStep())
                .build();
    }

    private Step groupByReservationStep(){
        return stepBuilderFactory.get("groupByReservationStep")
                .tasklet((contribution, chunkContext) -> {
                    LocalDateTime today = LocalDateTime.now();
                    String nowYyMmDd = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                    List<Object[]> groupByReservation = reservationRepository
                            .calculateSumOfReservationByDate(nowYyMmDd);

                    groupByReservation.forEach(objects -> {
                        Merchant merchant = merchantRepository.findById((int)objects[2])
                                   .orElseThrow(NotFoundInputException::new);
                        Seat seat = seatRepository.findById((long)objects[3])
                                   .orElseThrow(NotFoundInputException::new);

                        ReportReservation reportReservation = ReportReservation.createSimpleReportReservation(
                                 objects[0].toString()
                                ,objects[1].toString()
                                ,merchant
                                ,seat
                                ,(int)objects[4]
                        );

                        reportReservationRepository.save(reportReservation);
                    });

                    return RepeatStatus.FINISHED;
                }).build();
    }
}
