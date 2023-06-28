package com.seat.reservation.admin.controller;

import com.querydsl.core.Tuple;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* 가맹점 등록 (결제 방식 선택 기능(선.후불 유무)), 수정, 삭제, 조회1( 내 가맹점 조회 ), 조회2( 좌석 및 예약 상세 정보 조회 ) */

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MerchantAdminController {

    private final MerchantService merchantService;

    // 가맹점 등록
    @PostMapping("/merchant")
    public ResponseEntity<ResponseComDto> createMerchant(@RequestBody MerchantDto.create createMerchant) throws Exception {
        merchantService.registerMerchant(createMerchant);

        String msg = "새로운 가맹점 : " + createMerchant.getMerchantRegNum() + "가 등록되었습니다.";
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultMsg(msg)
                        .resultObj(createMerchant)
                        .build());
    }


    // 가맹점 업데이트
    @PutMapping("/merchant")
    public ResponseEntity<ResponseComDto> updateMerchant(@RequestBody MerchantDto.update updateMerchant) throws Exception {
        merchantService.updateMerchant(updateMerchant);
        String msg = "사업자 등록번호 : " + updateMerchant.getMerchantRegNum() + "가 갱신되었습니다.";
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultObj(msg)
                        .resultObj(updateMerchant)
                        .build());
    }

    // 가맹점리스트 조회
    @GetMapping("/merchant")
    public ResponseEntity<ResponseComDto> findMerchantList(MerchantDto.search search, Pageable pageable){
        Page<MerchantDto.show> merchantList = merchantService.findByMerchantList(search, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultObj("")
                        .resultObj(merchantList)
                        .build());
    }

    // 가맹점 상세 조회
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
