package com.seat.reservation.repository;

import com.seat.reservation.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Long, Item> {

}
