package com.seat.reservation.common.security;

public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String email){
            super(email + " NotFoundException");
        }

}
