package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.MerchantDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.SearchDto;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.MerchantHistoryRepository;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.SeatHistoryRepository;
import com.seat.reservation.common.repository.SeatRepository;
import com.seat.reservation.common.service.HistoryService;
import com.seat.reservation.common.service.MerchantService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor // 생성자 생성을 대신하자
@Transactional(readOnly = true)
public class MerchantServiceImpl extends SecurityService implements HistoryService, MerchantService {
    private final MerchantRepository merchantRepository;
    private final MerchantHistoryRepository merchantHistoryRepository;


    @Override
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

    @Override
    public void registerMerchant(Merchant merchant) throws Exception {
    }

    // 페이지에 사용자에 맞는 예약 정보를 가져오기 위함 -> reservation과 동일하다 생각
    @Override
    public Page<ReservationDto.show> selectReservations(SearchDto.date search, Pageable pageable) {
        User user = this.getUser().orElseThrow(()-> new NotFoundUserException("사용자 정보가 없습니다."));
        LocalDateTime startDateTime = search.getStartDateTime();
        LocalDateTime endDateTime = search.getEndDateTime();

        String userId = user.getUserid();
        Page<Reservation> reservations = merchantRepository.findByUserAndRegisterDateBetween(userId, startDateTime, endDateTime, pageable);
        List<ReservationDto.show> reservationList = reservations.get()
                .map(Reservation::convertSimpleReservationDtoShow)
                .collect(Collectors.toList());
        return new PageImpl<>(reservationList, pageable, reservations.getTotalElements());
    }


    @Override
    public MerchantDetailDto selectReservationDetail(Long reservationId) {
        return null;
    }







}
