package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.PaymentCode;
import com.seat.reservation.common.domain.enums.PaymentMethod;
import com.seat.reservation.common.listener.AuditHistoryEntityListener;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@ToString
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto Increment를 사용한 PK

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentCode paymentCode;

    private String authno; // 승인 번호

    private String cardNum; // 카드 번호

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    private int totalPrice;

    public void setTotalPrice(){
        this.totalPrice = reservation.getTotalPrice();
    }
}
