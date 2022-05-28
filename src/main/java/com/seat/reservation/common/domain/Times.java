package com.seat.reservation.common.domain;

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
@ToString
public class Times {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Auto Increment를 사용한 PK

    private int time;
}
