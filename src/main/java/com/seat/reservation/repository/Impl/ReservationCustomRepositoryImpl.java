package com.seat.reservation.repository.Impl;

import com.seat.reservation.domain.Reservation;
import com.seat.reservation.repository.custom.ReservationCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

    private EntityManager entityManager;

    @Override
    public Reservation findMyReservation() {
        return null;
    }
}

