package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // Basic CRUD로 사용. No need to update.
    Optional<User> findByUsername(String email);
    
}
