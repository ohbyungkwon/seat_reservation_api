package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto.create createUser(UserDto.create dto) throws Exception {
        Optional<User> user = userRepository.findByUserId(dto.getUserId());
        if(user.isPresent()){
            throw new BadReqException("이미 가입된 사용자입니다.");
        }

        return userRepository
                .save(User.createUser(dto, passwordEncoder))
                .convertDto();
    }
}
