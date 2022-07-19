package com.seat.reservation.common.repository;

import com.querydsl.core.group.GroupBy;
import com.seat.reservation.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Basic CRUD로 사용. No need to update.
    Optional<User> findByUsername(String email);

    Optional<User> findByEmail(String email);
}
