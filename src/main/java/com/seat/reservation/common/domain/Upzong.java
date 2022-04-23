package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.Category;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@SequenceGenerator(
        name = "UPZONG_SEQ_GENERATE",
        sequenceName = "UPZONG_SEQ"
)
public class Upzong {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "UPZONG_SEQ_GENERATE"
    )
    private Long id; // sequence

    private String code; // 업종 코드

    @JoinColumn
    @OneToMany(fetch = FetchType.LAZY)
    private List<Merchant> merchant;

    @Enumerated(value = EnumType.STRING)
    private Category category; // 카테고리 ex) pc방, cafe, hotel ...
}
