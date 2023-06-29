package com.seat.reservation.common.controller;

import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.ReviewDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<ResponseComDto> saveReview(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "review") ReviewDto.create create
        ) throws Exception {

        Boolean isSuccess = reviewService.saveReview(file, create);

        Map<String, Boolean> json = new HashMap<>();
        json.put("isSuccess", isSuccess);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("리뷰를 등록했습니다.")
                        .resultObj(json)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/review/{id}")
    public ResponseEntity<ResponseComDto> updateReview(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "review") ReviewDto.update update,
            @PathVariable Long id ) throws Exception {
        Long reviewId = update.getReviewId();
        if(!reviewId.equals(id)){
            throw new BadReqException("변경 정보가 부정확합니다.");
        }

        update.setReviewId(id);
        Boolean isSuccess = reviewService.updateReview(file, update);

        Map<String, Boolean> json = new HashMap<>();
        json.put("isSuccess", isSuccess);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("리뷰를 수정했습니다.")
                        .resultObj(json)
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<ResponseComDto> deleteReview(@PathVariable Long id) throws Exception {
        Boolean isSuccess = reviewService.deleteReview(id);

        Map<String, Boolean> json = new HashMap<>();
        json.put("isSuccess", isSuccess);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("리뷰를 삭제했습니다.")
                        .resultObj(json)
                        .build(), HttpStatus.OK);
    }
}
