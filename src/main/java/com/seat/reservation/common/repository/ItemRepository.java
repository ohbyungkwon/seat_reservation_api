package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByIdIn(List<Long> itemIdList);
}
