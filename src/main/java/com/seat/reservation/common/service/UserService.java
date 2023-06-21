package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.UserDto;

public interface UserService {
    UserDto.create createUser(UserDto.create user) throws Exception;
}
