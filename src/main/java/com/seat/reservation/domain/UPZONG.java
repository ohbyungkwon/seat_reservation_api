package com.seat.reservation.domain;

import com.seat.reservation.domain.enums.Category;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)

public class UPZONG {
    @Id
    private Long id; // 사장님 ID

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    private String menuName; // 메뉴

    private int price; // 상품 가격

    @Enumerated(value = EnumType.STRING)
    private Category category; // 카테고리 ex) pc방, cafe, hotel ...
}
