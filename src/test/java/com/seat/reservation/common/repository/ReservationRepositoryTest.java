package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.ItemDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ReservationItemDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationRepositoryTest {
    private static final Integer MERCHANT_ITEM_CNT = 2;
    private static final Integer RESERVATION_CNT = 21;
    private static final Integer MERCHANT_REG_NUM = 1;
    private static final Long SEAT_ID = 1L;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationItemRepository reservationItemRepository;

    /**
     * SAVE 테스트
     * 사용자 한명이 같은 가맹점 같은 상품을 20번 예약해봄
     */
//    @Test
//    @Rollback(value = false)
//    public void save() {
//        User customer = entityManager.find(User.class, "obk");
//        User merchantUser = entityManager.find(User.class, "SPC-Coworker");
//        if(customer == null || merchantUser == null) {
//            this.workBeforeReservation("SPC-Coworker", MERCHANT_REG_NUM);
//            //5. 예약 User 등록
//            customer = this.createUser("obk");
//        }
//
//        for(int i = 0; i < RESERVATION_CNT; i++) {
//            Reservation reservation = this.getReservation(customer);
//            List<Item> itemList = reservation.getMerchant().getItem();
//
//            //7. 예약 등록
//            reservation.setTotalPrice(itemList);
//            reservationRepository.save(reservation);
//            for (Item item : itemList) { //Merchant 모든 ITEM 에약
//                ReservationItem reservationItem = this.createReservationItem(item, reservation);
//                //8. 예약상품 저장
//                reservationItemRepository.save(reservationItem);
//            }
//        }
//    }

    public Reservation getReservation(User customer){
        System.out.println("=====================================");
        System.out.println("=====================================");

        //6. 가맹점 및 좌석 찾기(조인하여 한방 쿼리 가능)
        Merchant merchant = entityManager.find(Merchant.class, MERCHANT_REG_NUM);
        Seat seat = entityManager.find(Seat.class, SEAT_ID);
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

    /* createItem 메소드 수정으로 주석처리 */
//    public void workBeforeReservation(String merchantUserId, Integer merchantRegNum){
//        //1. 가맹점 등록 User 생성
//        User merchantUser = this.createUser(merchantUserId);
//
//        System.out.println("===========CASCADE, 한번에 PERSIST==============");
//        //2.Merchant 등록(Merchant 미구현으로 임시 사용)
//        Merchant merchant = this.createMerchant(merchantRegNum, merchantUser);
//        //3. item 등록(2 loop)
//        for(int i = 0; i < MERCHANT_ITEM_CNT; i ++) {
//            ItemDto.create create = ItemDto.create
//                    .builder()
//                    .price(1000 + i)
//                    .menuName("Food" + i)
//                    .build();
//            Item item = Item.createItem(create);
//            item.setMerchant(merchant);
//        }
//        //4. 좌석 등록(Seat 미구현으로 임시 사용)
//        this.createSeat(merchant);
//        System.out.println("==============================================");
//
//
//        //양방향 연관 관계 체크
//        Assertions.assertThat(merchant.getItem().size()).isEqualTo(MERCHANT_ITEM_CNT);
//    }

    public User createUser(String userId){
        User user = User.createUserSimple(userId);
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    public Merchant createMerchant(int merchantRegNum, User merchantUser){
        return Merchant.builder()
                .merchantRegNum(merchantRegNum)
                .user(merchantUser)
                .build();
    }

    public Seat createSeat(Merchant merchant){
        Seat seat = Seat.builder()
                .merchant(merchant)
                .build();
        entityManager.persist(seat);
        entityManager.flush();
        return seat;
    }


    @Test
    @Rollback(value = false)
    public void findByUser(){
        User my = entityManager.find(User.class, "obk");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Reservation> reservationPage = reservationRepository.findByUser(my, pageable);

        System.out.println("==============================================");
        System.out.println(reservationPage.toString());
        System.out.println("==============================================");

        Assertions.assertThat(reservationPage.getTotalPages()).isEqualTo(3);
    }

    @Test
    @Rollback(value = false)
    public void findByUserAndRegisterDateBetween() {
        User my = entityManager.find(User.class, "obk");
        Pageable pageable = PageRequest.of(0, 10);

        DateTimeFormatter dt =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse("2022-05-21 00:00:00", dt);
        LocalDateTime endDateTime = LocalDateTime.parse("2022-05-25 00:00:00", dt);
        Page<Reservation> reservationPage1 = reservationRepository.findByUserAndRegisterDateBetween(my.getUserid(), startDateTime, endDateTime, pageable);
        Assertions.assertThat(reservationPage1.getTotalPages()).isEqualTo(3);

        startDateTime = LocalDateTime.parse("2021-05-21 00:00:00", dt);
        endDateTime = LocalDateTime.parse("2021-05-25 00:00:00", dt);
        Page<Reservation> reservationPage2 = reservationRepository.findByUserAndRegisterDateBetween(my.getUserid(), startDateTime, endDateTime, pageable);
        Assertions.assertThat(reservationPage2.getTotalPages()).isEqualTo(0);
    }

    @Test
    @Rollback(value = false)
    public void findReservationDetail(){
        ReservationDto.show reservationDtoShow = reservationRepository.findReservationDetail(1L)
                .convertReservationDtoShow();
        System.out.println(reservationDtoShow.toString());
    }

    @Test
    @Rollback(value = false)
    public void findItemInReservationItem(){
        List<ReservationItemDto.show> reservationItemDtoShow = reservationItemRepository.findItemInReservationItem(1L)
                        .stream()
                        .map(ReservationItem::convertReservationItemDtoShow)
                        .collect(Collectors.toList());
        Assertions.assertThat(reservationItemDtoShow.size()).isEqualTo(2);
    }
}