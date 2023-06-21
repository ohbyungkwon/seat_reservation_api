package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.Gender;
import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.dto.UserDto;
import io.netty.util.internal.StringUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Optional;

@Slf4j
@Entity
@Builder
@Table(name = "member")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public class User {

    @Id
    private String userId; // userId. PK

    private String pw;

    private String name;

    private String phoneNum;

    private String email;

    private String birth;

    private String address;

    private int age; // birth로부터 계산. 따로 입력 받지는 않음.

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String snsType; // 로그인: sns 기반. ex ) kakao, apple, google, etc.

    private int loginFailCount; // 로그인 실패 횟수. 5회 이상 실패 시 계정 잠김 등 처리.

    private boolean isLocked; // 계정의 잠김 여부. ex) 비밀번호 다회 오입력 시 계정 잠김

    @Enumerated(EnumType.STRING)
    private Role role; // 관리자/사용자

    public static int getAge(String birth) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
        DateTime dt = formatter.parseDateTime(birth);
        return Years.yearsBetween(dt.toLocalDate(), now).getYears();
    }

    public void setIsLocked(boolean isLocked){
        this.isLocked = isLocked;
    }

    public static User createUserSimple(String userId){
        return User.builder().userId(userId).build();
    }

    public static User createUser(UserDto.create userDto, PasswordEncoder passwordEncoder){
        Role role = Optional.ofNullable(userDto.getRole())
                .orElse(Role.UNAUTHORIZATION_ROLE);
        String password = userDto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        log.debug("[password={}]", encodedPassword);

        return User.builder()
                .userId(userDto.getUserId())
                .pw(encodedPassword)
                .name(userDto.getName())
                .email(userDto.getEmail())
                .birth(userDto.getBirth())
                .address(userDto.getAddress())
                .gender(userDto.getGender())
                .role(role)
                .age(getAge(userDto.getBirth()))
                .build();
    }

    public UserDto.create convertDto(){
        return UserDto.create.builder()
                .userId(this.getUserId())
                .password(StringUtil.EMPTY_STRING)
                .name(this.getName())
                .email(this.getEmail())
                .birth(this.getBirth())
                .address(this.getAddress())
                .gender(this.getGender())
                .role(role)
                .age(getAge(this.getBirth()))
                .build();
    }
}
