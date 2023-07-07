package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link com.seat.reservation.common.service.impl.UserServiceImpl}
 */
public interface UserService {
    UserDto.create createUser(UserDto.create user) throws Exception;

    void updateUser(UserDto.update user) throws Exception;

    void manageRefreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
