package com.seat.reservation.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatHistory {
    @Id
    @CreatedDate
    private Date registerDate;

    @Id
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    private char registerCode;
}
