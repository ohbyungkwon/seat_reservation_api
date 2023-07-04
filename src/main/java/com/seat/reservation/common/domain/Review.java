package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.ReviewDto;
import lombok.*;
import org.springframework.util.StringUtils;

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

    @JoinColumn(name = "merchant_reg_num")
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant; // 리뷰를 단 가맹점

    private String title; // 리뷰 제목

    private String comment; // 리얼 리뷰

    @JoinColumn
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

    /**
     * parent가 null이면 최상위 리뷰
     * parent가 null이 아니면 상위 리뷰가 존재하는 하위 리뷰
     */
    public void joinParentReview(Review parent){
        this.parent = parent;
        if(parent != null)
            parent.getChildren().add(this);
    }

    public static Review createReview(ReviewDto.create dto, File file,
                                      Merchant merchant, Reservation reservation){
        return Review.builder()
                .merchant(merchant)
                .title(dto.getTitle())
                .comment(dto.getComment())
                .file(file)
                .reservation(reservation)
                .build();
    }

    public void changeReview(ReviewDto.update dto){
        String title = dto.getTitle();
        String comment = dto.getComment();

        if(!StringUtils.isEmpty(title)) {
            this.title = dto.getTitle();
        }
        if(!StringUtils.isEmpty(comment)) {
            this.comment = dto.getComment();
        }
    }

    /**
     * 대댓글은 따로 request 요청 작업 필요
     */
    public ReviewDto.showSimple convertShowSimpleDto(){
        return ReviewDto.showSimple.builder()
                .title(this.title)
                .comment(this.comment)
                .build();
    }
}
