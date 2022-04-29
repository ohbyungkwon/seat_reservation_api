package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // Basic CRUD로 사용. No need to update.
}
