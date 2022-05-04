//package com.seat.reservation.common.repository;
//
//import com.seat.reservation.common.domain.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.persistence.EntityManager;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//class ReservationRepositoryTest {
//
//    @Autowired
//    private ReservationRepository reservationRepository;
//
//    @Autowired
//    private EntityManager entityManager;
//
////    @Test
////    @Rollback(value = false)
////    public void findReservationDetailTest(){
////
////    }
//
//    @Test
//    @Rollback(value = false)
//    public void save(){
//        User merchantUser = this.createUser("spc");
//        User customer = this.createUser("obk");
//        Merchant merchant = this.createMerchant(1, merchantUser);
//        Seat seat = this.createSeat(merchant);
//    }
//
//
//    public Reservation createReservation(Seat seat, User customer, Merchant merchant){
//        Reservation reservation = Reservation.builder()
//                .seat(seat)
//                .user(customer)
//                .merchant(merchant)
//                .build();
//        entityManager.persist(reservation);
//        return reservation;
//    }
//
//    public User createUser(String userId){
//        User user = User.builder()
//                .userid(userId)
//                .build();
//        entityManager.persist(user);
//        return user;
//    }
//
//    public Merchant createMerchant(int merchantRegNum, User merchantUser){
//        Merchant merchant = Merchant.builder()
//                .merchantRegNumber(merchantRegNum)
//                .user(merchantUser)
//                .upzong(Upzong.builder().build())
//                .build();
//        entityManager.persist(merchant);
//        return merchant;
//    }
//
//    public Seat createSeat(Merchant merchant){
//        Seat seat = Seat.builder()
//                .merchant(merchant)
//                .build();
//        entityManager.persist(seat);
//        return seat;
//    }
//}