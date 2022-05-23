package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.dto.ItemDto;
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
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    private RegisterCode registerCode; // 등록 코드

    private String menuName;

    private int price;

    //양방향 관계는 create와 별개로 따로 설정하는게 안전함.
    public void setMerchant(Merchant merchant){
        this.merchant = merchant;
        merchant.getItem().add(this);
    }

    public static Item createItem(ItemDto.create itemDto){
        return Item.builder()
                .menuName(itemDto.getMenuName())
                .price(itemDto.getPrice())
                .build();
    }
}
