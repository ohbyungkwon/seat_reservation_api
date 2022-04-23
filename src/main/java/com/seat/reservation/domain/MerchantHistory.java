package com.seat.reservation.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MerchantHistory {
    @Id
    @CreatedDate
    private Date registerDate; // 가맹점 등록 일자

    private Date deleteDate;// 가맹점 삭제 일자

    @Id
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant; // 가맹점

    private char merchantRegisterCode; // 가맹점 등록 코드
}
