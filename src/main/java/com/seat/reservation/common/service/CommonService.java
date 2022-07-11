package com.seat.reservation.common.service;

import com.seat.reservation.common.domain.File;
import com.seat.reservation.common.dto.SearchDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommonService {
    List<SearchDto.time> getReservationAbleHours(Integer merchantRegNum);

    String renameFile(String filename);

    File getFile(MultipartFile file) throws Exception;
}
