package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Menu;
import com.seat.reservation.common.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {

    void deleteByMenuIdIn(List<String> menuIds);

    List<Menu> findByMenuNameLike(String menuName);

    List<Menu> findByMenuIdLike(String menuId);

    List<Menu> findByRole(Role role);
}

