package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.File;
import com.seat.reservation.common.dto.SearchDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CommonService {
    List<SearchDto.time> getReservationAbleHours(Integer merchantRegNum);

    String renameFile(String filename);

    File getFile(MultipartFile file) throws Exception;

    String getSaveFileName(String filename);

    Optional<File> findFile(Long fileId) throws Exception;

    void removeFile(File file) throws Exception ;
}
