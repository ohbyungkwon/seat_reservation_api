package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.Category;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.UpzongDto;
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
public class Upzong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // sequence

    private String code; // 업종 코드

    @JoinColumn
    @OneToMany(fetch = FetchType.LAZY)
    private List<Merchant> merchant;

    @Enumerated(value = EnumType.STRING)
    private Category category; // 카테고리 ex) pc방, cafe, hotel ...


    // Upzong 만드는 것
    public static Upzong createUpzong(UpzongDto.create dto,
                                      List<Merchant> merchant, Category category){
        return Upzong.builder() // 값을 받을 생성자를 선언한다.
                .code(dto.getCode())
                .category(category)
                .build();
    }





}
