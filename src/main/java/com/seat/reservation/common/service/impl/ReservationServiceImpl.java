package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ReservationItemDto;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.exception.NotOwnException;
import com.seat.reservation.common.repository.*;
import com.seat.reservation.common.repository.Impl.ReservationRepositoryImpl;
import com.seat.reservation.common.service.ReservationService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl extends SecurityService implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final ReservationItemRepository reservationItemRepository;
    private final MerchantRepository merchantRepository;
    private final SeatRepository seatRepository;
    private final ItemRepository itemRepository;

    /**
     * Reservation 저장
     * @param dto dto
     * @return Boolean
     */
    @Override
    public Boolean saveReservation(ReservationDto.create dto) {
        User user = this.getUser().orElseThrow(()-> new NotFoundUserException("사용자 정보가 없습니다."));
        Merchant merchant = merchantRepository.findByMerchantRegNum(dto.getMerchantRegNum());
        Optional<Seat> seat = seatRepository.findById(dto.getSeatId());
        if(seat.isPresent()){
            Reservation reservation = Reservation.createReservation(dto, merchant, seat.get(), user);
            reservationRepository.save(reservation);

            List<Item> itemList = itemRepository.findByIdIn(dto.getItemIdList());
            for(Item item : itemList){
                ReservationItem reservationItem = ReservationItem.createReservationItem(reservation, item);
                reservationItemRepository.save(reservationItem);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * Reservation 상세 조회
     * @param reservationId
     * @return ReservationDetailDto
     */
    @Override
    public ReservationDetailDto selectReservationDetail(Long reservationId) {
        User user = this.getUser().orElseThrow(()-> new NotFoundUserException("사용자 정보가 없습니다."));
        Boolean isExistUserReservation = reservationRepository.existsByIdAndUser(reservationId, user);
        if(isExistUserReservation == Boolean.FALSE){
            throw new NotOwnException("사용자의 예약이 아닙니다.");
        }

        ReservationDto.show reservation = reservationRepository.findReservationDetail(reservationId)
                .convertReservationDtoShow();
        List<ReservationItemDto.show> reservationItems = reservationItemRepository.findItemInReservationItem(reservationId)
                .stream()
                .map(ReservationItem::convertReservationItemDtoShow)
                .collect(Collectors.toList());

        return ReservationDetailDto.builder()
                .reservationInfo(reservation)
                .reservationItemInfo(reservationItems)
                .build();
    }
}
