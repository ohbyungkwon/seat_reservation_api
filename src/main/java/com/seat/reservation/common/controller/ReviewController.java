package com.seat.reservation.common.controller;

import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.ReviewDto;
import com.seat.reservation.common.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
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
}
