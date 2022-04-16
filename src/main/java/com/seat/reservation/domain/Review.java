package com.seat.reservation.domain;

import com.seat.reservation.user.controller.ReservationUserController;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class Review {
    private Long id;

    @JoinColumn
    @ManyToOne
    private Merchant merchant; // 리뷰를 단 가맹점

    private String title; // 리뷰 제목

    private String comment; // 리얼 리뷰

    @JoinColumn
    @OneToOne
    private Reservation reservation; // 예약 당 하나의 리뷰만 작성한다.
}
