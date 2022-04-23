package com.seat.reservation.repository;

import com.seat.reservation.domain.Merchant;
import com.seat.reservation.domain.MerchantHistory;
import com.seat.reservation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
}
