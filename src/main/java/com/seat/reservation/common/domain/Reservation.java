package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.ReservationDto;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    private int totalPrice;

    private boolean isPreOrder;

    private LocalDateTime reservationDate;

    @CreatedDate
    private LocalDateTime registerDate;

    @LastModifiedDate
    private LocalDateTime changeDate;

    public static Reservation createReservation(ReservationDto.create dto,
                                                Merchant merchant, Seat seat, User user){
        return Reservation.builder()
                .seat(seat)
                .user(user)
                .merchant(merchant)
                .isPreOrder(!dto.getItemIdList().isEmpty())
                .reservationDate(dto.getReservationDate())
                .build();
    }

    public void setIsPreOrder(List<Item> items){
        this.isPreOrder = !items.isEmpty();
    }

    public void setTotalPrice(List<Item> items){
        for(Item item : items)
            this.totalPrice += item.getPrice();
    }

    public ReservationDto.show convertReservationDtoShow(){
        return ReservationDto.show.builder()
                .totalPrice(this.totalPrice)
                .isPreOrder(this.isPreOrder)
                .reservationDate(this.reservationDate)
                .seatCode(this.seat.getSeatCode())
                .repPhone(this.merchant.getRepPhone())
                .merchantTel(this.merchant.getMerchantTel())
                .merchantName(this.merchant.getMerchantName())
                .address(this.merchant.getAddress())
                .zipCode(this.merchant.getZipCode())
                .reservationCost(this.seat.getReservationCost())
                .build();
    }
}
