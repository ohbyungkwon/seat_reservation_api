package com.seat.reservation.repository;

import com.seat.reservation.domain.Item;
import com.seat.reservation.domain.Merchant;
import com.seat.reservation.domain.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByMerchant(Merchant merchant, Pageable pageable);

    Page<Item> findByMerchantAndCategory(Merchant merchant, Category category, Pageable pageable);
}
