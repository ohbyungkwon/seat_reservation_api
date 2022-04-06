package com.seat.reservation.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String birth;

    private String address;
}
