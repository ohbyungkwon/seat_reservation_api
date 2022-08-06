package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findByMenuName(String menuName);

    List<Menu> findByMenuId(String menuId);

    List<Menu> findByMenuIdAndMenuName(String menuId, String menuName);
}

