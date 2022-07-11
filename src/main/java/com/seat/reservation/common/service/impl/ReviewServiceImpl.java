package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.File;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.Review;
import com.seat.reservation.common.dto.ReviewDto;
import com.seat.reservation.common.repository.FileRepository;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.ReservationRepository;
import com.seat.reservation.common.repository.ReviewRepository;
import com.seat.reservation.common.service.CommonService;
import com.seat.reservation.common.service.ReviewService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CommonService commonService;
    private final MerchantRepository merchantRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public Boolean saveReview(MultipartFile file, ReviewDto.create create) throws Exception {
        File attachedFile = commonService.getFile(file);

        Long parentId = create.getParentId();
        Review parentReview = parentId != null ?
                reviewRepository.findById(parentId).orElseGet(() -> null) : null;

        Merchant merchant = merchantRepository.findByMerchantRegNum(create.getMerchantRegNum());
        Reservation reservation = reservationRepository.findById(create.getReservationId())
                .orElseThrow(() ->  new NotFoundException("예약한 고객만 리뷰 작성 가능합니다."));

        Review review = Review.createReview(create, parentReview, attachedFile, merchant, reservation);
        reviewRepository.save(review);

        return Boolean.TRUE;
    }
}
