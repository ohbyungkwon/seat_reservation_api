package com.seat.reservation.common.listener;

import com.seat.reservation.common.domain.*;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.CommonRepository;
import com.seat.reservation.common.service.SecurityService;
import com.seat.reservation.common.exception.NotSupportHistoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class AuditHistoryEntityListener extends SecurityService {
    @Lazy
    @Autowired
    private CommonRepository commonRepository;

    @PreUpdate
    @PreDestroy
    public void preUpdate(Object entity) {
        Object id = this.getId(entity);
        Object currentObj = commonRepository.findById(entity.getClass(), id);
        Object historyEntity = this.getHistoryEntity(currentObj);
        commonRepository.save(historyEntity);
    }

    private Object getId(Object entity) {
        Object id = null;
        if (entity instanceof Merchant) {
            id = ((Merchant) entity).getMerchantRegNumber();
        } else if (entity instanceof Seat) {
            id = ((Seat) entity).getId();
        }

        if (id == null) {
            throw new NotSupportHistoryException(entity.getClass() + "는 Listener를 지원하지 않습니다.");
        }

        return id;
    }

    private Object getHistoryEntity(Object currentObj) {
        User user = this.getUser().orElseThrow(() ->
                new NotFoundUserException("사용자를 찾을 수 없습니다."));

        Object historyTable = null;
        if (currentObj instanceof Merchant) {
            Merchant merchant = (Merchant) currentObj;
            historyTable = MerchantHistory.builder()
                    .registerDate(LocalDateTime.now())
                    .merchant(merchant)
                    .merchantRegisterCode(merchant.getRegisterCode())
                    .build();
        } else if (currentObj instanceof Seat) {
            Seat seat = (Seat) currentObj;
            historyTable = SeatHistory.builder()
                    .seat(seat)
                    .registerDate(LocalDateTime.now())
                    .registerCode(seat.getRegisterCode())
                    .user(user)
                    .build();
        }

        if (historyTable == null) {
            throw new NotSupportHistoryException(currentObj.getClass() + "는 Listener를 지원하지 않습니다.");
        }

        return historyTable;
    }
}
