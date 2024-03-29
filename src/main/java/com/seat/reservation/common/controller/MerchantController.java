package com.seat.reservation.common.controller;

/* 첫 화면에서 보여주는 가맹점 대충 보여주기 (업종별, 지역별, 날짜, 인원수 설정에 따라 예약 가능 여부 확인할 수 있게)*/
/* 가맹점 누르면 가맹점별 상세 조회 보여준다. (좌석들(예약되어 있냐), 리뷰정보) */

import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * {@link com.seat.reservation.admin.controller.MerchantAdminController}
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    /**
     * @param search
     * @param pageable
     * @return ResponseEntity<ResponseComDto>
     * - 가맹점 리스트 조회
     */
    @GetMapping("/merchant")
    public ResponseEntity<ResponseComDto> findMerchantList(MerchantDto.search search, Pageable pageable){
        Page<MerchantDto.show> merchantList = merchantService.findByMerchantList(search, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultObj("")
                        .resultObj(merchantList)
                        .build());
    }

    /**
     * @param merchantRegNum
     * @return ResponseEntity<ResponseComDto>
     * - 가맹점 상세 조회
     */
    @GetMapping("/merchant/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> findMerchant(@PathVariable Integer merchantRegNum){
        MerchantDto.showDetail merchantDetail = merchantService.findByMerchantDetail(merchantRegNum);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultObj("")
                        .resultObj(merchantDetail)
                        .build());
    }
}
