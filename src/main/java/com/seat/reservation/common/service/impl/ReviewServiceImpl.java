package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.File;
import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.Review;
import com.seat.reservation.common.dto.FileDto;
import com.seat.reservation.common.dto.ReviewDto;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.FileRepository;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.ReservationRepository;
import com.seat.reservation.common.repository.ReviewRepository;
import com.seat.reservation.common.service.CommonService;
import com.seat.reservation.common.service.ReviewService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CommonService commonService;
    private final MerchantRepository merchantRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 리뷰 하나에 하나 사진만 관리
     * @param file
     * @param create
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean saveReview(MultipartFile file, ReviewDto.create create) throws Exception {
        File attachedFile = commonService.getFile(file);

        Long parentId = create.getParentId();
        Review parentReview = parentId != null ?
                reviewRepository.findById(parentId).orElseGet(() -> null) : null;

        Merchant merchant = merchantRepository.findByMerchantRegNum(create.getMerchantRegNum());
        Reservation reservation = reservationRepository.findById(create.getReservationId())
                .orElseThrow(() ->  new NotFoundException("예약한 고객만 리뷰 작성 가능합니다."));

        Review review = Review.createReview(create, attachedFile, merchant, reservation);
        review.joinParentReview(parentReview);
        reviewRepository.save(review);
        return Boolean.TRUE;
    }

    /**
     * @param file (파일이 null이면 삭제, 파일이 있으면 유지 혹은 변경)
     * @param update
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean updateReview(MultipartFile file, ReviewDto.update update) throws Exception {
        Long reviewId = update.getReviewId();
        if(reviewId != null) {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new NotFoundUserException("리뷰가 존재하지 않습니다."));
            review.changeReview(update);

            //file 없는 경우(삭제)
            if(file.isEmpty()){
                File removeFile = review.getFile();
                if(removeFile == null)//원래 파일이 없는 경우
                    return Boolean.TRUE;

                commonService.removeFile(removeFile);
            } else {
                Long changeFileId = update.getChangeFileId();
                if (changeFileId == null) {
                    File updateFile = commonService.findFile(changeFileId)
                            .orElseThrow(() -> new NotFoundUserException("파일이 존재하지 않습니다."));

                    String filename = file.getOriginalFilename();
                    FileDto.create fileDto = FileDto.create.builder()
                            .filename(filename)
                            .saveFilename(commonService.getSaveFileName(filename))
                            .binary(file.getBytes())
                            .mimeType(file.getContentType())
                            .build();

                    updateFile.changeFile(fileDto);
                }
            }
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * @param reviewId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean deleteReview(Long reviewId) throws Exception {
        Review deleteReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundUserException("리뷰가 존재하지 않습니다."));
        reviewRepository.delete(deleteReview);
        return Boolean.TRUE;
    }
}
