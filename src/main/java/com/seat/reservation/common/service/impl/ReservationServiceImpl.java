package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.dto.ReservationDetailDto;
import com.seat.reservation.common.dto.ReservationDto;
import com.seat.reservation.common.dto.ReservationItemDto;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.exception.NotOwnException;
import com.seat.reservation.common.repository.*;
import com.seat.reservation.common.service.ReservationService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * [메타 정책]
 * 예약은 30분 단위로 가능하다.
 * 1시30분 예약 가능
 * 1시10분, 20분 예약 불가능
 * 가맹점주는 가맹점관리에서 이용시간 설정 가능하다.
 * 예를 들어 2시간 이용시간 가맹점이 있다면 아래와 같이 예약이 가능하다.
 * 1. 10:00~12:00
 * 2. 10:30~12:30
 * 3. 11:00~13:00
 * ...
 * I. 12:00~14:00
 * 만약 1번이 예약되었다면 다음 예약자는 I번부터 얘약 가능하다.
 *
 * [예약 프로세스]
 * 가맹점 선택
 * -> 좌석 예약 상황 보여줌(위와 같은 시간으로 조회)
 * -> 예약하기 클릭시, 메뉴 리스트 출력
 * -> 메뉴 선택 후, 예약 진행
 * -> (가맹점 선택사항) 사장님에게 알림, 최종 확인 필요
 * */
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
     * @param dto
     * @return Boolean
     */
    @Override
    public Boolean saveReservation(ReservationDto.create dto) {
        User user = this.getUser().orElseThrow(()-> new NotFoundUserException("사용자 정보가 없습니다."));
        Merchant merchant = merchantRepository.findByMerchantRegNum(dto.getMerchantRegNum());
        Optional<Seat> seat = seatRepository.findById(dto.getSeatId());
        if(seat.isPresent()){
            List<Item> itemList = itemRepository.findByIdIn(dto.getItemIdList());
            Reservation reservation = Reservation.createReservation(dto, merchant, seat.get(), user);
            reservation.setTotalPrice(itemList);
            reservationRepository.save(reservation);
            for(Item item : itemList){
                ReservationItem reservationItem = ReservationItem.createReservationItem(reservation, item);
                reservationItemRepository.save(reservationItem);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 나의 Reservation 리스트 조회
     * @param pageable
     * @return Page<ReservationDto.show>
     */
    @Override
    public Page<ReservationDto.show> selectReservations(ReservationDto.search search, Pageable pageable) {
        User user = this.getUser().orElseThrow(()-> new NotFoundUserException("사용자 정보가 없습니다."));
        LocalDateTime startDateTime = search.getStartDateTime();
        LocalDateTime endDateTime = search.getEndDateTime();
        Page<Reservation> reservations = reservationRepository.findByUserAndRegisterDateBetween(user, startDateTime, endDateTime, pageable);
        List<ReservationDto.show> reservationList = reservations.get()
                .map(Reservation::convertReservationDtoShow)
                .collect(Collectors.toList());
        return new PageImpl<>(reservationList, pageable, reservations.getTotalElements());
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
