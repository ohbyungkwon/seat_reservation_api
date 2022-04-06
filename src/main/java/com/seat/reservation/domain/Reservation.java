package com.seat.reservation.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@SequenceGenerator(
        name = "RESERVATION_SEQ_GENERATE",
        sequenceName = "RESERVATION_SEQ"
)
public class Reservation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RESERVATION_SEQ_GENERATE"
    )
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private int totalPrice;

    private boolean isPreOrder;

    private Date reservationDate;

    @CreatedDate
    private Date registerDate;

    @LastModifiedDate
    private Date changeDate;
}
