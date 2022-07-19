package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.domain.Merchant;
import com.seat.reservation.common.domain.PaymentHistory;
import com.seat.reservation.common.domain.Reservation;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.PaymentMethod;
import com.seat.reservation.common.dto.PayDto;
import com.seat.reservation.common.service.PaymentService;
import com.seat.reservation.common.support.ApplicationContextProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

// Payment Module Factory
@AllArgsConstructor
@Component
public class PayModule {

    public PaymentService getPayService(PaymentMethod paymentMethod){
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        if(paymentMethod == PaymentMethod.TOSS){
            return applicationContext.getBean(TossPayServiceImpl.class);
        }
        else if(paymentMethod == PaymentMethod.KAKAO){
            return applicationContext.getBean(KakaoPayServiceImpl.class);
        }
        else if(paymentMethod == PaymentMethod.MONEY){
            return applicationContext.getBean(MoneyPayServiceImpl.class);
        }
        else if(paymentMethod == PaymentMethod.NAVER){
            return applicationContext.getBean(NaverPayServiceImpl.class);
        }
        else if(paymentMethod == PaymentMethod.CARD){
            return applicationContext.getBean(CardPayServiceImpl.class);
        }
        else{
            return applicationContext.getBean(CardPayServiceImpl.class);
        }
    }

    public PaymentHistory pay(PaymentService paymentService, Reservation reservation
            , User user
            , Merchant merchant
            , PayDto.InputPayDto payDto){
        PayDto.OutputPayDto paymentResult = paymentService.pay(reservation, payDto);

        return PaymentHistory.builder()
                .paymentMethod(paymentService.getPaymentMethod())
                .paymentCode(paymentResult.getPaymentCode())
                .authno(paymentResult.getAuthNo())
                .cardNum(payDto.getCardNo())
                .user(user)
                .merchant(merchant)
                .reservation(reservation)
                .totalPrice(reservation.getTotalPrice())
                .build();
    }
}
