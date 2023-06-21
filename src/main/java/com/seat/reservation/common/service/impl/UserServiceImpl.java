package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto.create createUser(UserDto.create dto) throws Exception {
        User user = User.createUser(dto, passwordEncoder);
        return userRepository.save(user)
                .convertDto();
    }
}
