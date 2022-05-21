package com.seat.reservation.common.listener;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.service.impl.MerchantServiceImpl;
import com.seat.reservation.common.service.impl.SeatServiceImpl;
import com.seat.reservation.common.service.HistoryService;
import com.seat.reservation.common.support.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Component
public class AuditHistoryEntityListener {
    @PreUpdate
    @PreRemove
    public void preUpdateAndDestroy(Object entity) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        HistoryService historyService = null;
        if(entity instanceof Seat){
            historyService = applicationContext.getBean(SeatServiceImpl.class);
        }
        else if(entity instanceof  Merchant){
            historyService = applicationContext.getBean(MerchantServiceImpl.class);
        }
        else{
            historyService = null;
        }

        historyService.historySave(entity);
    }
}
