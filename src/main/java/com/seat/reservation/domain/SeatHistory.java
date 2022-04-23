package com.seat.reservation.domain;

import com.seat.reservation.domain.enums.RegisterCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "HISTORY_SEQ_GENERATE",
        sequenceName = "HISTORY_SEQ"
)
public class SeatHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "HISTORY_SEQ_GENERATE"
    )
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat; // 등록한 좌석

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registerDate; // 좌석을 등록한 날짜

    @Enumerated(EnumType.STRING)
    private RegisterCode registerCode; // 등록 코드

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 좌석을 등록한 유저
}
