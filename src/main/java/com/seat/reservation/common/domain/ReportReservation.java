package com.seat.reservation.common.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportReservation {
    @Id
    private Long id;

    private String yyMmDd;

    private String hh24;

    @ManyToOne
    private Merchant merchant;

    @ManyToOne
    private Seat seat;

    private int totalPrice;

    public static ReportReservation createSimpleReportReservation(
        String yyMmDd,
        String hh24,
        Merchant merchant,
        Seat seat,
        int totalPrice
    ){
        return ReportReservation.builder()
                .yyMmDd(yyMmDd)
                .hh24(hh24)
                .merchant(merchant)
                .seat(seat)
                .totalPrice(totalPrice).build();
    }
}
