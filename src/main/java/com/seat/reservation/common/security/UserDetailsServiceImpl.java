package com.seat.reservation.common.security;

import com.seat.reservation.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * This bean finds a user and convert 'User' to 'UserDetails' object.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public MyUserDetails loadUserByUsername(String username) {
        return userRepository.findByUserId(username)
                .map(MyUserDetails::new)
                .orElseThrow(() -> new UserNotFoundException(username));
    }


}
