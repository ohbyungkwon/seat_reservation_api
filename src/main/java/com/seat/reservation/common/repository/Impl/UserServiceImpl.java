package com.seat.reservation.common.repository.Impl;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.service.UserService;
import com.seat.reservation.dao.UserDAO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Inject
    public UserServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @Override
    public void registerUser(User user) throws Exception{
        userDAO.register(user);
    }
}
