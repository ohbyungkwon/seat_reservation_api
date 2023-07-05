package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.MenuDto;

import java.io.IOException;
import java.util.List;

/**
 * {@link com.seat.reservation.common.service.impl.MenuServiceImpl}
 */
public interface MenuService {

    void createMenus(List<MenuDto.create> create);

    void deleteMenus(List<String> menuIds);

    List<MenuDto.search> searchMenu(MenuDto.search search);

    List<MenuDto.search> searchUserMenu(String key) throws IOException;
}
