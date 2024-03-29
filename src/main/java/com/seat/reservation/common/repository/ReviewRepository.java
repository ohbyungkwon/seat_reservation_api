package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findById(Long reviewId);

    List<Review> findByMerchant(Merchant merchant);
}
