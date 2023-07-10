package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.Gender;
import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.exception.BadReqException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private int age; // 생일에서 계산. 따로 입력 받지는 않음.

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int loginFailCount; // 로그인 실패 횟수. 5회 이상 실패 시 계정 잠김 등 처리.

    private boolean isNeedChangePw; // 비밀번호 찾기를 이용한 고객(임시비번 사용일 경우)
    private boolean isLocked; // 계정의 잠김 여부. ex) 비밀번호 다회 오입력 시 계정 잠김

    @Setter
    private LocalDate lastLoginDate;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate lastModifiedDate;

    @Enumerated(EnumType.STRING)
    private Role role; // 관리자/사용자

    public static int getAge(String birth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        int nowYear = LocalDate.now().getYear();
        int birthYear = LocalDate.parse(birth, formatter).getYear();
        return nowYear - birthYear + 1; // 만 나이 x
    }

    public int plusLoginFailCount(){
        return (this.loginFailCount == 5 ? loginFailCount : (this.loginFailCount += 1));
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static User createUser(UserDto.create userDto, PasswordEncoder passwordEncoder) {
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
                .isNeedChangePw(false)
                .isLocked(false)
                .loginFailCount(0)
                .build();
    }

    public void updateUser(UserDto.update userDto, PasswordEncoder passwordEncoder) throws Exception {
        boolean isCorrect = passwordEncoder.matches(userDto.getOldPassword(), this.pw);
        if (!isCorrect) {
            throw new BadReqException("현재 비밀번호를 확인해주세요.");
        }

        String address = userDto.getAddress();
        if (!StringUtils.isEmpty(address)) {
            this.address = address;
        }

        String password = userDto.getPassword();
        if (!StringUtils.isEmpty(password)) {
            this.changePw(password, passwordEncoder);
            this.setIsLocked(false);
            this.setIsNeedChangePw(false);
        }
    }

    public void changePw(String password, PasswordEncoder passwordEncoder){
        this.pw = passwordEncoder.encode(password);
    }

    public void setIsNeedChangePw(boolean isNeedChangePw){
        this.isNeedChangePw = isNeedChangePw;
    }

    public UserDto.create convertDto(){
        return UserDto.create.builder()
                .userId(this.getUserId())
                .password("")
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
