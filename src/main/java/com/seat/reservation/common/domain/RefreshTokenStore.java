package com.seat.reservation.common.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seat.reservation.common.security.TokenUtils;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenStore {
    // Principal을 User로 사용하지 않기 때문에 따로 연관관계 설정하지 않음
    @Id
    private String userId;

    private String cookieValue;

    @Column(length = 1000)
    private String refreshToken;

    public static String generateCookieValue() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    public static RefreshTokenStore createRefreshTokenStore(String userId) throws JsonProcessingException {
        return RefreshTokenStore.builder()
            .cookieValue(generateCookieValue())
            .refreshToken(TokenUtils.generateJwtToken(userId))
            .userId(userId)
            .build();
    }

    public void renewRefreshTokenStore() throws JsonProcessingException {
        this.cookieValue = generateCookieValue();
        this.refreshToken = TokenUtils.generateJwtToken(this.userId);
    }
}
