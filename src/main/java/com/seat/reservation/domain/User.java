package com.seat.reservation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@Builder
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
