package com.seat.reservation.common.service.impl;

import com.seat.reservation.admin.service.SeatAdminService;
import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.*;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.repository.*;
import com.seat.reservation.common.service.HistoryService;
import com.seat.reservation.common.service.MerchantService;
import com.seat.reservation.common.service.SeatService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MerchantServiceImpl extends SecurityService implements HistoryService, MerchantService {

    private final MerchantRepository merchantRepository;

    private final MerchantHistoryRepository merchantHistoryRepository;

    private final SeatService seatService;

    private final SeatAdminService seatAdminService;

    private final UpzongRepository upzongRepository;

    private final ReviewRepository reviewRepository;

    /**
     * @param entity
     * - 가맹점 정보 History 저장
     */
    @Override
    @Transactional
    public void historySave(Object entity) {
        Merchant merchant = (Merchant) entity;
        Merchant beforeMerchant = merchantRepository.findByMerchantRegNum(merchant.getMerchantRegNum());
        if(beforeMerchant != null) {
            log.info("MERCHANT HISTORY INSERT!!!");
            MerchantHistory merchantHistory = MerchantHistory.builder()
                    .merchant(beforeMerchant)
                    .merchantRegisterCode(beforeMerchant.getRegisterCode())
                    .build();

            merchantHistoryRepository.save(merchantHistory);
        }
    }

    /**
     * @param merchantDto
     * @throws Exception
     * - 가맹점 정보 등록
     */
    @Override
    @Transactional
    public void registerMerchant(MerchantDto.create merchantDto) throws Exception {
        Long upzongId = merchantDto.getUpzongId();
        Upzong upzong = upzongRepository.findById(upzongId)
                .orElseThrow(() -> new BadReqException("업종 정보를 확인해주세요."));

        int merchantRegNum = merchantDto.getMerchantRegNum();
        Merchant merchant = merchantRepository.findByMerchantRegNum(merchantRegNum);
        if(merchant != null) throw new BadReqException("존재하는 가맹점입니다.");

        User user = this.getUser()
                .orElseThrow(() -> new BadReqException("사용자 정보가 없습니다."));
        Merchant newMerchant = Merchant.createMerchant(merchantDto, upzong, user);
        newMerchant.setUpzong(upzong); //양방향 관계

        List<SeatDto.create> seatList = merchantDto.getSeatList();
        if(!ListUtils.isEmpty(seatList)){
            seatAdminService.createSeats(seatList);
        }

        merchantRepository.save(newMerchant);
    }

    /**
     * @param merchantDto
     * @throws Exception
     * - 가맹점 정보 수정
     */
    @Override
    @Transactional
    public void updateMerchant(MerchantDto.update merchantDto) throws Exception {
        Merchant merchant = merchantRepository.findById(merchantDto.getMerchantRegNum())
                .orElseThrow(() -> new BadReqException("존재하지 않는 가맹점입니다."));

        Long upzongId = merchantDto.getUpzongId();
        Upzong upzong = upzongRepository.findById(upzongId)
                .orElseThrow(() -> new BadReqException("업종 정보를 확인해주세요."));

        merchant.updateMerchant(merchantDto, upzong);
    }

    /**
     * @param search
     * @param pageable
     * @return Page<MerchantDto.show>
     * - 가맹점 리스트 조회
     */
    @Override
    public Page<MerchantDto.show> findByMerchantList(MerchantDto.search search, Pageable pageable) {
        return merchantRepository.findMerchantList(search, pageable);
    }


    /**
     * @param merchantRegNum
     * @return MerchantDto.showDetail
     * - 가맹점 상세 정보 조회
     */
    @Override
    public MerchantDto.showDetail findByMerchantDetail(Integer merchantRegNum) {
        Merchant merchant = merchantRepository.findMerchantDetail(merchantRegNum);
        MerchantDto.showMerchantDetail merchantDetail = merchant.convertMerchantDetailDto();
        List<ReviewDto.showSimple> reviewList =
                reviewRepository.findByMerchant(merchant).stream()
                        .map(Review::convertShowSimpleDto)
                        .collect(Collectors.toList());

        List<SeatDto.showByTime> seatList = seatService.searchUseAbleSeat(merchantRegNum, null);
        return MerchantDto.showDetail.builder()
                .merchantDetail(merchantDetail)
                .reviewList(reviewList)
                .seatList(seatList)
                .build();
    }
}
