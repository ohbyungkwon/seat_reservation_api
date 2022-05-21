package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.enums.RegisterCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SeatRepositoryTest {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MerchantRepository merchantRepository;

    @Test
    public void findSeatTest(){
        System.out.println("TEST TEST TEST");

        Seat seat = getSeat(102L);

        System.out.println(seat);
    }

    @Test
    public void saveSeatTest(){
        Seat seat = Seat.createSeat(new String("A6222")
                , getMerchant(8828100)
                , true
                , RegisterCode.REGISTER);

        seatRepository.saveAndFlush(seat);

        List<Seat> seats = seatRepository.findAll();

        System.out.println("======ALL SEATS======");
        seats.forEach((entity)-> System.out.println(entity));
    }

    public Merchant getMerchant(Integer merchantRegNum){
        return merchantRepository.findByMerchantRegNum(merchantRegNum);
    }

    @Test
    public void updateSeatTest(){
        Seat seat = getSeat(254L);

        System.out.println("============");
        System.out.println("BEFORE" + seat);

        seat.setIsUse(true);
        seat.setRegisterCode(RegisterCode.CHANGE);

        System.out.println("============");
        System.out.println("AFTER" + seat);

        seatRepository.save(seat);

        List<Seat> seats = seatRepository.findAll();

        seats.forEach((entity)-> System.out.println(entity));
    }

    @Test
    public void deleteSeatTest(){
        /* 1. setRegisterCode를 적어야하는 점 (개선할 방법이 있는지???) */
        /* 2. SEAT_HISTORY 테이블에 SEAT FORIGN KEY가 걸려있어서 DELETE가 안됨 ????? 이거 확인 */

        Seat seat = seatRepository.findById(102L).orElse
                (Seat.createSeat(new String("V222")
                        , null
                        , true
                        , RegisterCode.REGISTER));

        seat.setRegisterCode(RegisterCode.DELETE);

        System.out.println("============");
        System.out.println(seat);

        seatRepository.delete(seat);
    }

    public Seat getSeat(Long seatId){
        Seat seat = seatRepository.findById(seatId).orElse
                (Seat.createSeat(new String("V222")
                        , null
                        , true
                        , RegisterCode.REGISTER));

        return seat;
    }

}