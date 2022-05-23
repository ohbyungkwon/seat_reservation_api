package com.seat.reservation.common.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Table
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant; // 리뷰를 단 가맹점

    private String title; // 리뷰 제목

    private String comment; // 리얼 리뷰

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation; // 예약 당 하나의 리뷰만 작성한다.
}
