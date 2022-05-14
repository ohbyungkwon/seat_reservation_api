package com.seat.reservation.common.dto;

import com.seat.reservation.common.domain.enums.Gender;
import com.seat.reservation.common.domain.enums.Role;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {
    @Data
    @Builder
    public static class create {
        @Length(min=4, max = 8)
        @NotEmpty(message = "username null")
        private String userId;

        @Length(min = 6)
        @NotEmpty(message = "password null")
        private String password;

        @NotEmpty(message = "name null")
        private String name;

        @Email
        @NotEmpty(message = "email null")
        private String email;

        @Length(max = 6)
        @NotEmpty(message = "birth null")
        private String birth;

        private int age;

        @NotEmpty(message = "address null")
        private String address;

        @Enumerated(EnumType.STRING)
        private Gender gender;

        @Enumerated(EnumType.STRING)
        private Role role;
    }
}
