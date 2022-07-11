package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.ReviewDto;
import lombok.*;

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

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private File file;

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

    public static Review createReview(ReviewDto.create dto, Review parent, File file,
                                      Merchant merchant, Reservation reservation){
        return Review.builder()
                .merchant(merchant)
                .title(dto.getTitle())
                .comment(dto.getComment())
                .file(file)
                .parent(parent)
                .reservation(reservation)
                .build();
    }
}
