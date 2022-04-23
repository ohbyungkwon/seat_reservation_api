package com.seat.reservation.common.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table
@Entity
@Builder
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
public class User {
    @Id
    private String userid;

    private String password;

    private String name;

    private String email;

    private String birth;

    private String address;

    private int age;

    private String snsType;

    private int loginFailCount;

    private boolean isLocked;

    private String roll;


}
