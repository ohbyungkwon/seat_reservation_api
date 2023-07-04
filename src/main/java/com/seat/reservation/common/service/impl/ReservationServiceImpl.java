package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.domain.enums.PaymentCode;
import com.seat.reservation.common.dto.*;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.exception.NotOwnException;
import com.seat.reservation.common.repository.*;
import com.seat.reservation.common.service.PaymentService;
import com.seat.reservation.common.service.ReservationService;
import com.seat.reservation.common.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
@Transactional(readOnly = true)
public class ReservationServiceImpl extends SecurityService implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final ReservationItemRepository reservationItemRepository;
    private final MerchantRepository merchantRepository;
    private final SeatRepository seatRepository;
    private final ItemRepository itemRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentManager paymentManager;


    /**
     * @param dto
     * @return Boolean
     * - Reservation 저장
     */
    @Override
    @Transactional
    public Boolean saveReservation(ReservationDto.create dto, PayDto.InputPayDto inputPayDto) throws IOException {
        User user = this.getUser().orElseThrow(()-> new NotFoundUserException("사용자 정보가 없습니다."));
        Merchant merchant = merchantRepository.findByMerchantRegNum(dto.getMerchantRegNum());
        Optional<Seat> seatOptional = seatRepository.findById(dto.getSeatId());
        if(seatOptional.isPresent()){
            Seat seat = seatOptional.get();
            List<Item> itemList = itemRepository.findByIdIn(dto.getItemIdList());
            Reservation reservation = Reservation.createReservation(dto, merchant, seat, user);
            reservation.setTotalPrice(itemList);

            PaymentService paymentService = paymentManager.getPayService(inputPayDto.getPaymentMethod());
            PaymentHistory paymentHistory = paymentManager.pay(paymentService, reservation, user, merchant, inputPayDto);
            paymentHistoryRepository.save(paymentHistory);
            if(paymentHistory.getPaymentCode() != PaymentCode.APPROVE){
                return Boolean.FALSE;
            }

            reservationRepository.save(reservation);
            for(Item item : itemList){
                ReservationItem reservationItem = ReservationItem.createReservationItem(reservation, item);
                reservationItemRepository.save(reservationItem);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * @param reservationId
     * @param inputPayDto
     * @return Boolean
     * - 예약 취소
     */
    @Override
    @Transactional
    public Boolean removeReservation(Long reservationId, PayDto.InputPayDto inputPayDto) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundUserException("예약 정보를 찾을 수 없습니다."));

        String msg = reservation.isPossibleCancel();
        if(!msg.equals("")){
            throw new BadReqException(msg);
        }

        PaymentService paymentService = paymentManager.getPayService(inputPayDto.getPaymentMethod());
        PaymentHistory paymentHistory = paymentManager.pay(paymentService, reservation,
                reservation.getUser(), reservation.getMerchant(), inputPayDto);
        paymentHistoryRepository.save(paymentHistory);
        if(paymentHistory.getPaymentCode() != PaymentCode.APPROVE){
            return Boolean.FALSE;
        }

        if(reservation.isPreOrder()) {
            List<Long> reservationItemIdList = reservationItemRepository.findByReservationId(reservationId)
                    .stream().map(ReservationItem::getId).collect(Collectors.toList());
            reservationItemRepository.deleteByIdIn(reservationItemIdList);
        }
        reservationRepository.delete(reservation);
        return Boolean.TRUE;
    }

    /**
     * @param pageable
     * @return Page<ReservationDto.show>
     * - 나의 Reservation 리스트 조회
     */
    @Override
    public Page<ReservationDto.show> selectReservations(SearchDto.date search, Pageable pageable) throws IOException {
        User user = this.getUser().orElseThrow(()-> new NotFoundUserException("사용자 정보가 없습니다."));
        LocalDateTime startDateTime = search.getStartDateTime();
        LocalDateTime endDateTime = search.getEndDateTime();

        String userId = user.getUserId();
        Page<Reservation> reservations = reservationRepository.findByUserAndRegisterDateBetween(userId, startDateTime, endDateTime, pageable);
        List<ReservationDto.show> reservationList = reservations.get()
                .map(Reservation::convertSimpleReservationDtoShow)
                .collect(Collectors.toList());
        return new PageImpl<>(reservationList, pageable, reservations.getTotalElements());
    }

    /**
     * @param reservationId
     * @return ReservationDetailDto
     * - Reservation 상세 조회
     */
    @Override
    public ReservationDetailDto selectReservationDetail(Long reservationId) throws IOException {
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

    /**
     * @param update
     * @return Boolean
     * - 예약 진행 완료처리
     */
    @Override
    public Boolean completeReservation(ReservationDto.update update) {
        Long reservationId = update.getReservationId();
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BadReqException("예약 정보가 존재하지 않습니다."));

        reservation.setReservationEndDateTime(update.getRealUseDate());
        reservation.cancelUsingSeat();
        return Boolean.TRUE;
    }
}
