package com.seat.reservation.common.repository.custom;

import com.seat.reservation.common.domain.MenuAuthority;
import com.seat.reservation.common.dto.MenuDto;

import java.util.List;

public interface MenuAuthorityRepositoryCustom {
    List<MenuAuthority> findMenuWithUserInfo(String userId);
}
