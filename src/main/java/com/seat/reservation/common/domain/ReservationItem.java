package com.seat.reservation.common.domain;

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
@SequenceGenerator(
        name = "RESERVATION_ITEM_SEQ_GENERATE",
        sequenceName = "RESERVATION_ITEM_SEQ"
)
public class ReservationItem {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RESERVATION_ITEM_SEQ_GENERATE"
    )
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Reservation reservation;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;


    public static ReservationItem createReservationItem(Reservation reservation,
                                                        Item item){
        return ReservationItem.builder()
                .reservation(reservation)
                .item(item)
                .build();
    }
}
