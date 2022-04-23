package com.seat.reservation.common.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CommonRepository {
    private EntityManager entityManager;

    public CommonRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> T findById(Class<T> cls, Object id){
        return entityManager.find(cls, id);
    }

    public <T> T save(T entity){
        entityManager.persist(entity);
        return entity;
    }

    public <T> void delete(T entity) {
        entityManager.remove(entity);
    }
}
