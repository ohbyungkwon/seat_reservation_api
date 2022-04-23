package com.seat.reservation.domain;

import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
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

    @LastModifiedDate
    private LocalDateTime ChangeDate; // 데이터가 바뀐날짜
}
