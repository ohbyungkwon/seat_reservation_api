package com.seat.reservation.repository;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.repository.SeatHistoryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.*;

@SpringBootTest
public class SeatRepositoryTest {

    @Autowired
    SeatHistoryRepository seatHistoryRepository;

    @Test
    void objectTest(){

     //   seatHistoryRepository.findByUserWithPaging(new User()
     //           , PageRequest.of(0, 1, Sort.by(Sort.Order.desc("registerDate")))).getContent();






    }

}