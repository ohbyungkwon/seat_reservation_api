package com.seat.reservation.domain;

import com.seat.reservation.domain.enums.RegisterCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatHistory {
    @Id
    @CreatedDate
    private Date registerDate; // 좌석을 등록한 날짜

    @Id
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat; // 등록한 좌석

    @Enumerated(EnumType.STRING)
    private RegisterCode registerCode; // 등록 코드

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 좌석을 등록한 유저
}
