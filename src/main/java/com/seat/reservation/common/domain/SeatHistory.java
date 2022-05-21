package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.RegisterCode;
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
        name = "SEAT_HISTORY_SEQ_GENERATE",
        sequenceName = "SEAT_HISTORY_SEQ"
)
public class SeatHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEAT_HISTORY_SEQ_GENERATE"
    )
    private Long id;

    /* seat id 로 변경 */
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Seat seat; // 등록한 좌석

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registerDate; // 좌석을 등록한 날짜

    @Enumerated(EnumType.STRING)
    private RegisterCode registerCode; // 등록 코드

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 좌석을 등록한 유저
}
