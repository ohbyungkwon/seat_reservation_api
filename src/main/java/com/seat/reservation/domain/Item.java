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
@SequenceGenerator(
        name = "ITEM_SEQ_GENERATE",
        sequenceName = "ITEM_SEQ"
)
public class Item {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ITEM_SEQ"
    )
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    private String menuName;

    private int price;

    @Enumerated(value = EnumType.STRING)
    private Category category;
}
