package com.seat.reservation.admin.controller;

import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * {@link com.seat.reservation.common.controller.MerchantController}
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MerchantAdminController {

    private final MerchantService merchantService;


    /**
     * @param createMerchant
     * @return ResponseEntity<ResponseComDto>
     * @throws Exception
     * - 가맹점 등록
     */
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

    /**
     * @param updateMerchant
     * @return ResponseEntity<ResponseComDto>
     * @throws Exception
     * - 가맹점 수정
     */
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
}
