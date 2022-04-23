package com.seat.reservation.common.listener;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.CommonRepository;
import com.seat.reservation.common.repository.service.SeatService;
import com.seat.reservation.common.repository.service.Service;
import com.seat.reservation.common.service.SecurityService;
import com.seat.reservation.common.exception.NotSupportHistoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class AuditHistoryEntityListener  {
    @PrePersist
    @PreUpdate
    @PreRemove
    public void preUpdateAndDestroy(Object entity) {
        Service service;

        if(entity instanceof Seat){
            service = new SeatService();
        }
        else if(entity instanceof  Merchant){
           // service = new MerchantService();
            service = null;
        }
        else{
            service = null;
        }

        service.historySave(entity);
    }
}
