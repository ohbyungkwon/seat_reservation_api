package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.ReservationItemDto;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
public class ReservationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "reservation_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;


    public static ReservationItem createReservationItem(Reservation reservation,
                                                        Item item){
        return ReservationItem.builder()
                .reservation(reservation)
                .item(item)
                .build();
    }

    public ReservationItemDto.show convertReservationItemDtoShow(){
        return ReservationItemDto.show.builder()
                .menuName(this.item.getMenuName())
                .price(this.item.getPrice())
                .build();
    }
}
