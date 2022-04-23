package com.seat.reservation.common.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Builder
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
@SequenceGenerator(
        name = "LOGIN_HISTORY_SEQ_GENERATE",
        sequenceName = "LOGIN_HISTORY_SEQ"
)
public class LoginHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LOGIN_HISTORY_SEQ_GENERATE"
    )
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User userid;

    private boolean isSuccess;

    @CreatedDate
    private Date loginDate;
}
