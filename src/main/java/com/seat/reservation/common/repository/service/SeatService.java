package com.seat.reservation.common.repository.service;

import com.seat.reservation.common.domain.Seat;
import com.seat.reservation.common.repository.SeatRepository;
import com.seat.reservation.common.service.SecurityService;
import com.seat.reservation.common.support.ApplicationContextProvider;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class SeatService extends SecurityService implements Service{

    @Override
    public void save(Object object) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        SeatRepository seatRepository = applicationContext.getBean(SeatRepository.class);

        seatRepository.save((Seat)object);
    }

    @Override
    public Object find(Object object) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        SeatRepository seatRepository = applicationContext.getBean(SeatRepository.class);

        return seatRepository.findById(((Seat)object).getId());
    }

    @Override
    public void historySave(Object object) {
        Seat seat = (Seat)this.find(object);

      //  seat save 한다.

        System.out.println("HISTORY INSERT!!!");
    }
}
