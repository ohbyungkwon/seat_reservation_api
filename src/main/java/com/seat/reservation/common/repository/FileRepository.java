package com.seat.reservation.common.repository;

import com.seat.reservation.common.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Boolean existsByFilename(String filename);
}
