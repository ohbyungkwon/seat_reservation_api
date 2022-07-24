package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.MenuAuthority;
import com.seat.reservation.common.repository.custom.MenuAuthorityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuAuthorityRepository extends JpaRepository<MenuAuthority, String>, MenuAuthorityRepositoryCustom {
}
