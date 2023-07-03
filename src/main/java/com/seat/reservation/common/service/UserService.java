package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    UserDto.create createUser(UserDto.create user) throws Exception;

    void updateUser(UserDto.update user) throws Exception;

    void manageRefreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
