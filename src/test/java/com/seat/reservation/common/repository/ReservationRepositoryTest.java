package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.ItemDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationItemRepository reservationItemRepository;

    /**
     * SAVE 테스트
     */
    @Test
    @Rollback(value = false)
    public void save() {
        this.workBeforeReservation();
        Reservation reservation = getReservation();
        reservationRepository.save(reservation);

        List<Item> itemList = reservation.getMerchant().getItem();
        for(Item item: itemList) { //Merchant 모든 ITEM 에약
            ReservationItem reservationItem = this.createReservationItem(item, reservation);
            reservationItemRepository.save(reservationItem);
        }
    }

    public Reservation getReservation(){
        System.out.println("=====================================");
        System.out.println("=====================================");

        User customer = User.createUserSimple("obk");
        entityManager.persist(customer);
        entityManager.flush();

        Merchant merchant = entityManager.find(Merchant.class, 1);
        Seat seat = entityManager.find(Seat.class, 1L);
        return this.createReservation(seat, customer, merchant);
    }

    public Reservation createReservation(Seat seat, User customer, Merchant merchant){
        return Reservation.builder()
                .seat(seat)
                .user(customer)
                .merchant(merchant)
                .build();
    }

    public ReservationItem createReservationItem(Item item, Reservation reservation){
        return ReservationItem.builder()
                .reservation(reservation)
                .item(item)
                .build();
    }

    public void workBeforeReservation(){
        User merchantUser = User.createUserSimple("SPC-Coworker");
        entityManager.persist(merchantUser);
        entityManager.flush();

        //Merchant 미구현으로 임시 사용
        Merchant merchant = this.createMerchant(1, merchantUser);
        entityManager.persist(merchant);
        //Seat 미구현으로 임시 사용
        Seat seat = this.createSeat(merchant);
        entityManager.persist(seat);

        int itemCnt = 2;
        for(int i = 0; i < itemCnt; i ++) {
            ItemDto.create create = ItemDto.create
                    .builder()
                    .price(1000 + i)
                    .menuName("Food" + i)
                    .build();
            Item item = Item.createItem(create);
            item.setMerchant(merchant);
            entityManager.persist(item);
        }
        entityManager.flush();

        Assertions.assertThat(merchant.getItem().size()).isEqualTo(itemCnt);
    }

    public Merchant createMerchant(int merchantRegNum, User merchantUser){
        return Merchant.builder()
                .merchantRegNum(merchantRegNum)
                .user(merchantUser)
                .build();
    }

    public Seat createSeat(Merchant merchant){
        return Seat.builder()
                .merchant(merchant)
                .build();
    }
}