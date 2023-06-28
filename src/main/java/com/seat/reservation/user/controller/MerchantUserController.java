package com.seat.reservation.user.controller;

/* 첫 화면에서 보여주는 가맹점 대충 보여주기 (업종별, 지역별, 날짜, 인원수 설정에 따라 예약 가능 여부 확인할 수 있게)*/
/* 가맹점 누르면 가맹점별 상세 조회 보여준다. (좌석들(예약되어 있냐), 리뷰정보) */

import com.querydsl.core.Tuple;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/merchant")
@RequiredArgsConstructor
public class MerchantUserController {

    private final MerchantService merchantService;

    // 가맹점 정보를 보여주는
    @GetMapping("/merchant")
    public ResponseEntity<ResponseComDto> selectMerchantList(MerchantDto.search search, Pageable pageable){
        Page<MerchantDto.show> merchantList = merchantService.findByMerchantList(search, pageable);
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("가맹점 리스트 조회 완료")
                        .resultObj(merchantList)
                        .build(), HttpStatus.OK);
    }


    // 가맹점 상제 정보를 보여주는
    @GetMapping("/merchant/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> selectMerchantDetail(@PathVariable Integer merchantRegNum){
        MerchantDto.showDetail merchantDetail = merchantService.findByMerchantDetail(merchantRegNum);
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("가맹점 상세 조회 완료")
                        .resultObj(merchantDetail)
                        .build(), HttpStatus.OK);
    }
}
