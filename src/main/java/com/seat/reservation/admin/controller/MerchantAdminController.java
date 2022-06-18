package com.seat.reservation.admin.controller;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.domain.Upzong;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.MerchantDto;
import com.seat.reservation.common.dto.ResponseComDto;
import com.seat.reservation.common.dto.SeatDto;
import com.seat.reservation.common.repository.Impl.MerchantRepositoryImpl;
import com.seat.reservation.common.repository.Impl.SeatRepositoryImpl;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.SeatRepository;
import com.seat.reservation.common.repository.UpzongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/* 가맹점 등록 (결제 방식 선택 기능(선.후불 유무)), 수정, 삭제, 조회1( 내 가맹점 조회 ), 조회2( 좌석 및 예약 상세 정보 조회 ) */

@RestController
@RequestMapping("/admin/merchant")
@RequiredArgsConstructor
public class MerchantAdminController {

    private final UpzongRepository upzongRepository;
    private final MerchantRepository merchantRepository;
    private final MerchantRepositoryImpl merchantRepositoryImpl;

    // 가맹점 등록
    @PostMapping(path = "/create-merchant")
    public ResponseEntity<ResponseComDto> createMerchant(@RequestBody MerchantDto.create createMerchant){

        // 가맹점이 등록되어있는지 확인하기 위해서 가맹점의 PK를 가져 온다.
        Merchant merchant = merchantRepository.findByMerchantRegNum(createMerchant.getMerchantRegNum());

        // 가맹점 존재 여부 판단
        if(merchant == null){
            System.out.println("존재하지 않는 가맹점입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                            .resultMsg("존재하지 않는 가맹점입니다.")
                            .resultObj(createMerchant).build());

        }

        Merchant newMerchant = Merchant.createMerchant(createMerchant.getMerchantRegNum()
                , createMerchant.getRepName()
                , createMerchant.getRepPhone()
                , createMerchant.getMerchantTel()
                , createMerchant.getMerchantName()
                , createMerchant.getUpzongId()
                , createMerchant.getAddress()
                , createMerchant.getZipCode());

        merchantRepository.save(newMerchant);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                        .resultMsg("새로운 가맹점 : " + createMerchant.getMerchantRegNum() + "가 등록되었습니다.")
                        .resultObj(createMerchant).build());
    }


    // 가맹점 업데이트
    @PutMapping(path = "/merchant")
    public ResponseEntity<ResponseComDto> updateMerchant(@RequestParam MerchantDto.update updateMerchant){
        Optional<Merchant> merchant = merchantRepository.findById(updateMerchant.getMerchantRegNum());

        if(merchant.isPresent()){
            merchant.get().setRegisterCode(updateMerchant.getRegisterCode());
            merchantRepository.save(merchant.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                            .resultObj("존재하지 않는 가맹점 입니다.")
                            .resultObj(updateMerchant).build());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseComDto.builder()
                        .resultObj("사업자 등록번호 : " + updateMerchant.getMerchantRegNum() + "가 갱신되었습니다.")
                        .resultObj(updateMerchant).build());
    }

    // 가맹점 삭제
    @DeleteMapping(path = "/merchant/{merchantRegNum}")
    public ResponseEntity<ResponseComDto> deleteMerchant(@PathVariable(value = "merchantRegNum") Integer merchantRegNum){
        Optional<Merchant> merchant = merchantRepository.findById(merchantRegNum);

        if(merchant.isPresent()){
            merchant.get().setRegisterCode(RegisterCode.DELETE);
            merchantRepository.save(merchant.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                            .resultObj("존재하지 않는 가맹점 입니다.")
                            .resultObj(merchantRegNum).build());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseComDto.builder()
                        .resultObj("사업자 등록번호 : " + merchantRegNum + "가 삭제되었습니다.")
                        .resultObj(merchantRegNum).build());
    }

    // 가맹점 조회
    @GetMapping(path = "/seat/{merchant_reg_num}")
    public ResponseEntity<ResponseComDto> findMerchant(@PathVariable(value = "merchant_reg_num") Integer merchantRegNum){


        // 가맹점이 등록되어있는지 확인하기 위해서 가맹점의 PK를 가져 온다.
        Merchant merchant_temp = merchantRepository.findByMerchantRegNum(merchantRegNum);


        if(merchant_temp == null){
            System.out.println("존재하지 않는 가맹점입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseComDto.builder()
                            .resultMsg("존재하지 않는 가맹점입니다.")
                            .resultObj(merchantRegNum).build());
        }

        List<Merchant> Merchants = merchantRepositoryImpl.findMerchant(merchantRegNum);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseComDto.builder()
                        .resultMsg("검색한 사업자 등록번호 : " + merchantRegNum + "에 대한 가맹점 리스트입니다.")
                        .resultObj(merchantRegNum).build());
    }





}
