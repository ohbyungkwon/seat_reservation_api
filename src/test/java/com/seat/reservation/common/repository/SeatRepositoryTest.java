package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.repository.Impl.SeatRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SeatRepositoryTest {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    SeatRepositoryImpl seatRepositoryImpl;

    @Test
    public void findSeatTest(){
        System.out.println("TEST TEST TEST");

        Seat seat = getSeat(102L);

        System.out.println(seat);
    }

    @Test
    public void saveSeatTest(){
        Seat seat = Seat.createSeat("A6222"
                , getMerchant(1)
                , 100
                , RegisterCode.REGISTER);

        seatRepository.saveAndFlush(seat);

        List<Seat> seats = seatRepository.findAll();

        System.out.println("======ALL SEATS======");
        seats.forEach(System.out::println);
    }

    public Merchant getMerchant(Integer merchantRegNum){
        return merchantRepository.findByMerchantRegNum(merchantRegNum);
    }

    @Test
    public void updateSeatTest(){
        Seat seat = getSeat(254L);

        System.out.println("============");
        System.out.println("BEFORE" + seat);

        seat.setRegisterCode(RegisterCode.CHANGE);

        System.out.println("============");
        System.out.println("AFTER" + seat);

        seatRepository.save(seat);

        List<Seat> seats = seatRepository.findAll();

        seats.forEach(System.out::println);
    }

    @Test
    public void deleteSeatTest(){
        /* 1. setRegisterCode를 적어야하는 점 (개선할 방법이 있는지???) */
        /* 2. SEAT_HISTORY 테이블에 SEAT FORIGN KEY가 걸려있어서 DELETE가 안됨 ????? 이거 확인 */

        Seat seat = seatRepository.findById(102L).orElse
                (Seat.createSeat("V222"
                        , null
                        , 100
                        , RegisterCode.REGISTER));

        seat.setRegisterCode(RegisterCode.DELETE);

        System.out.println("============");
        System.out.println(seat);

        seatRepository.save(seat);
    }

    public Seat getSeat(Long seatId){
        return seatRepository.findById(seatId).orElse
                (Seat.createSeat("V222"
                        , null
                        , 100
                        , RegisterCode.REGISTER));
    }

    @Test
    public void findSeatByMerchantRegNumTest(){
        List<SeatDto.show>  seats = seatRepositoryImpl.findSeatInMerchant(8828100);

        seats.forEach(System.out::println);
    }

    @Test
    public void findSeatByReservationTime(){
        List<SeatDto.showByTime> seats = seatRepositoryImpl.findSeatByTime(1
                , LocalDateTime.of(2022, 5, 23, 13, 0)
//                , LocalDateTime.of(2022, 5, 23, 14, 0)
        );

        seats.forEach(System.out::println);
    }


}