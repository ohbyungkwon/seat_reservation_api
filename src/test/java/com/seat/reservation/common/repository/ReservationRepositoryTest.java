package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.dto.ItemDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationItemRepository reservationItemRepository;

    @Test
    @Rollback(value = false)
    public void findReservationDetailTest(){
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void workBeforeReservation(){
        User merchantUser = User.createUserSimple("spc");
        entityManager.persist(merchantUser);
        entityManager.flush();

        //Merchant 미구현으로 임시 사용
        Merchant merchant = this.createMerchant(1, merchantUser);
        //Seat 미구현으로 임시 사용
        this.createSeat(merchant);

        for(int i = 0; i < 2; i ++) {
            ItemDto.create create = ItemDto.create
                    .builder()
                    .price(1000 + i)
                    .menuName("Food" + i)
                    .build();
            Item item = Item.createItem(create);
            item.setMerchant(merchant);
            entityManager.persist(item);
        }
    }

    public Reservation createReservation(Seat seat, User customer, Merchant merchant){
        Reservation reservation = Reservation.builder()
                .seat(seat)
                .user(customer)
                .merchant(merchant)
                .build();
        entityManager.persist(reservation);
        return reservation;
    }

    public Merchant createMerchant(int merchantRegNum, User merchantUser){
        Merchant merchant = Merchant.builder()
                .merchantRegNum(merchantRegNum)
                .user(merchantUser)
                .build();
        entityManager.persist(merchant);
        return merchant;
    }

    public Seat createSeat(Merchant merchant){
        Seat seat = Seat.builder()
                .merchant(merchant)
                .build();
        entityManager.persist(seat);
        return seat;
    }
}