package com.seat.reservation.common.domain;

import com.seat.reservation.common.dto.MenuDto;
import com.seat.reservation.common.dto.ReservationDto;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public class Menu {
    @Id
    private String menuId;

    private String menuName;

    public MenuDto.search convertMenuDtoShow() {
        return MenuDto.search.builder()
                .menuId(this.menuId)
                .menuName(this.menuName)
                .build();
    }
}
