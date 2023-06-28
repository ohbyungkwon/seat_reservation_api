package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.ReservationDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "merchant_reg_num")
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    private int totalPrice;

    private boolean isPreOrder;

    private LocalDateTime reservationDate;

    private LocalDateTime realUseDate;

    @CreatedDate
    private LocalDateTime registerDate;

    @LastModifiedDate
    private LocalDateTime changeDate;

    public static Reservation createReservation(ReservationDto.create dto,
                                                Merchant merchant, Seat seat, User user){

        seat.setIsUse(Boolean.TRUE);
        return Reservation.builder()
                .seat(seat)
                .user(user)
                .merchant(merchant)
                .isPreOrder(!dto.getItemIdList().isEmpty())
                .reservationDate(dto.getReservationDate())
                .realUseDate(dto.getReservationDate())
                .build();
    }

    public void setIsPreOrder(List<Item> items){
        this.isPreOrder = !items.isEmpty();
    }

    public void setRealUseDate(LocalDateTime date) { this.realUseDate = date; }

    public void setTotalPrice(List<Item> items){
        for(Item item : items)
            this.totalPrice += item.getPrice();
    }

    public void cancelUsingSeat(){
        this.seat.setIsUse(Boolean.FALSE);
    }

    public ReservationDto.show convertSimpleReservationDtoShow(){
        return ReservationDto.show.builder()
                .totalPrice(this.totalPrice)
                .isPreOrder(this.isPreOrder)
                .reservationDate(this.reservationDate)
                .realUserDate(this.realUseDate)
                .repPhone(this.merchant.getRepPhone())
                .merchantTel(this.merchant.getMerchantTel())
                .merchantName(this.merchant.getMerchantName())
                .address(this.merchant.getAddress())
                .zipCode(this.merchant.getZipCode())
                .build();
    }

    //Reservation Cost는 무엇인가?
    public ReservationDto.show convertReservationDtoShow(){
        return ReservationDto.show.builder()
                .totalPrice(this.totalPrice)
                .isPreOrder(this.isPreOrder)
                .reservationDate(this.reservationDate)
                .realUserDate(this.realUseDate)
                .seatCode(this.seat.getSeatCode())
                .repPhone(this.merchant.getRepPhone())
                .merchantTel(this.merchant.getMerchantTel())
                .merchantName(this.merchant.getMerchantName())
                .address(this.merchant.getAddress())
                .zipCode(this.merchant.getZipCode())
                .reservationCost(this.seat.getReservationCost())
                .build();
    }

    public String isPossibleCancel(){
        String msg = "";
        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDate today = todayDateTime.toLocalDate();
        LocalDate reservationDate = this.getReservationDate().toLocalDate();
        LocalDate registerDate = this.getRegisterDate().toLocalDate();

        //당일 예약
        if(registerDate.equals(reservationDate)){
            //결제 후 1시간 지남
            if (this.getRegisterDate().plusHours(1).isBefore(todayDateTime)) {
                msg = "한시간이 지난 결제는 취소할 수 없습니다.";
            }
        } else {
            //예약 당일
            if (today.equals(reservationDate)) {
                if (todayDateTime.plusHours(1).isAfter(this.getReservationDate())) {
                    msg = "예약 1시간 이전 취소는 불가합니다.";
                }
            }
        }
        return msg;
    }
}
