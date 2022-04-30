package com.seat.reservation.common.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
