package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.MenuDto;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.List;

/**
 * {@link com.seat.reservation.common.service.impl.MenuServiceImpl}
 */
public interface MenuService {
    List<MenuDto.search> searchAllMenu(MenuDto.search search);

    @Cacheable(value = "reservation_api_cache_name", key = "#key")
    List<MenuDto.searchAll> searchUserMenu(String key) throws IOException;
}
