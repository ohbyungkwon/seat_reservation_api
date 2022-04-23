package com.seat.reservation.common.repository.service;

public interface Service {
    void save(Object object);
    Object find(Object object);
    void historySave(Object object);
}
