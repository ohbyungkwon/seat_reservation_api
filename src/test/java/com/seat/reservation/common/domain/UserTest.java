package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.Gender;
import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    @Rollback(value = false)
    public void createUserSimple(){
        User user = User.createUserSimple("obk");
        Assertions.assertThat(user.getUserid()).isEqualTo("obk");
    }

    @Test
    @Rollback(value = false)
    public void createUser() {
        User user = User.createUser(this.createDto());
        Assertions.assertThat(user.getUserid()).isEqualTo("obk");
        Assertions.assertThat(user.getAge()).isEqualTo(28);
    }

    public UserDto.create createDto(){
        return UserDto.create.builder()
                .userId("obk")
                .password("1234")
                .name("obk")
                .email("obk@test.com")
                .birth("19930617")
                .address("test test test")
                .gender(Gender.MAN)
                .role(Role.NORMAL_ROLE)
                .build();
    }
}