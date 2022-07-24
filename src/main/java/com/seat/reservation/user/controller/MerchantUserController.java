package com.seat.reservation.user.controller;

/* 첫 화면에서 보여주는 가맹점 대충 보여주기 (업종별, 지역별, 날짜, 인원수 설정에 따라 예약 가능 여부 확인할 수 있게)*/
/* 가맹점 누르면 가맹점별 상세 조회 보여준다. (좌석들(예약되어 있냐), 리뷰정보) */

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.service.MerchantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
@RequestMapping("/merchant")
public class MerchantUserController {

    private final MerchantService merchantService;

    @Inject
    public MerchantUserController(MerchantService merchantService){
        this.merchantService = merchantService;
    }


    // 가맹점 정보를 보여주는
    //   @GetMapping("/merchant/{merchantRegNum}")
//    public Merchant findBySelectMerchant(@PathVariable SearchDto.date search, Pageable pageable){
//        return merchantService.selectMerchant(SearchDto.date search, Pageable pageable);
//    }


    // 가맹점 상제 정보를 보여주는
    //   @GetMapping("/merchant/{merchantRegNum}")
//    public Merchant findBySelectMerchantDetail(@PathVariable Integer merchantRegNum){
//        return merchantService.selectMerchantDetail(merchantRegNum);
//    }


}
