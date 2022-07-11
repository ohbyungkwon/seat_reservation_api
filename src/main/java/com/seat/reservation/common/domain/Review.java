package com.seat.reservation.common.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private byte[] image;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Review parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = true)
    private List<Review> children = new ArrayList<Review>();

    /**
     * 예약당 하나의 리뷰만 작성된다
     * 나의 예약 정보에서 댓글을 달 수 있다.
     */
    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;
}
