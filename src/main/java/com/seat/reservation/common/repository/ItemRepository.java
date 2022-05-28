package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Item;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByMerchant(Merchant merchant, Pageable pageable);

    List<Item> findByIdIn(List<Long> itemIdList);
//    Page<Item> findByMerchantAndCategory(Merchant merchant, Category category, Pageable pageable);
}
