package com.seat.reservation.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Role implements GrantedAuthority {
    UNAUTHORIZATION_ROLE("UNAUTHORIZATION_ROLE"),
    NORMAL_ROLE("NORMAL_ROLE"),
    ADMIN_ROLE("ADMIN_ROLE");

    private String value;

    @Override
    public String getAuthority() {
        return this.value;
    }
}
