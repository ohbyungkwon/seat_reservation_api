package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    // Basic CRUD로 사용. No need to update.
    Optional<User> findByUsername(String email);

    @Query(value =  "SELECT U.USER_ID" +
                    "  FROM USER U" +
                    "  LEFT OUTER JOIN " +
                    "       (SELECT H.USER_ID" +
                    "             , MAX(LOGIN_DATE) MAX_DATE" +
                    "          FROM LOGIN_HISTORY H" +
                    "         WHERE H.IS_SUCCESS = 'TRUE'" +
                    "         GROUP BY H.USER_ID) HH" +
                    "WHERE U.USER_ID = HH.USER_ID" +
                    "  AND U.IS_LOCKED = 'FALSE'" +
                    "  AND HH.MAX_DATE < DATE_SUB(NOW(), INTERVAL 90 DAY)"
            , nativeQuery = true)
    List<User> findUserByLoginTimeBeforeThreeMonth();
}
