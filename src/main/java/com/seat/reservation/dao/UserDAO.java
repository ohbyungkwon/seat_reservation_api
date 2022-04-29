package com.seat.reservation.dao;

import com.seat.reservation.common.domain.User;

public interface UserDAO {
    void register(User user) throws Exception;
}
