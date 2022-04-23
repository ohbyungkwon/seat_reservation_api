package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.listener.AuditHistoryEntityListener;
import lombok.*;
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
@EntityListeners(value = { AuditHistoryEntityListener.class, AuditingEntityListener.class})
@SequenceGenerator(
        name = "SEAT_SEQ_GENERATE",
        sequenceName = "SEAT_SEQ"
)

/* 주석 추가 */
public class Seat {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEAT_SEQ_GENERATE"
    )
    private Long id; // Auto Increment를 사용한 PK

    private String seatCode; // 좌석 번호

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant; // 좌석을 가진 가맹점

    private int reservationCost; // 좌석 예약 비용

    private boolean isUse; // 사용중

    @Enumerated(EnumType.STRING)
    private RegisterCode registerCode; // 등록 코드

    @CreatedDate
    private LocalDateTime registerDate; // 데이터가 바뀐날짜

    @LastModifiedDate
    private LocalDateTime changeDate; // 데이터가 바뀐날짜
}
