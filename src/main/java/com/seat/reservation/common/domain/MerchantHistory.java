package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.RegisterCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "HISTORY_SEQ_GENERATE",
        sequenceName = "HISTORY_SEQ"
)
public class MerchantHistory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "HISTORY_SEQ_GENERATE"
    )
    private Long id;

    @CreatedDate
    private LocalDateTime registerDate; // 가맹점 등록 일자 PK는 시퀀스로 가고

    // id -> 이 부분을 없애고 id는 가맹점 등록일자로만 가는걸로 설정
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant; // 가맹점

    @Enumerated(EnumType.STRING)
    private RegisterCode merchantRegisterCode; // 가맹점 등록 코드
}
