package com.seat.reservation.domain;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
@SequenceGenerator(
        name = "LOGIN-HISTORY_SEQ_GENERATE",
        sequenceName = "LOGIN-HISTORY_SEQ"
)
public class LoginHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LOGIN-HISTORY_SEQ_GENERATE"
    )
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User userid;

    private boolean isSuccess;

    @LastModifiedDate
    private Date loginDate;
}
