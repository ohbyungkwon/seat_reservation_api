package com.seat.reservation.domain;

import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@SequenceGenerator(
        name = "SEAT_SEQ_GENERATE",
        sequenceName = "SEAT_SEQ"
)
public class Seat {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEAT_SEQ_GENERATE"
    )
    private Long id;
    @Id
    private Long seatNo;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant Merchant;

    private int reservation_cost;

    private boolean is_use;

    @LastModifiedDate
    private Date ChangeDate;
}
