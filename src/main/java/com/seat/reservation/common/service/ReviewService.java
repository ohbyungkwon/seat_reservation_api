package com.seat.reservation.common.service;

import com.seat.reservation.common.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {
    Boolean saveReview(MultipartFile file, ReviewDto.create create) throws Exception;
}
