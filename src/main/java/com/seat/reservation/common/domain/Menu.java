package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.Role;
import com.seat.reservation.common.dto.MenuDto;
import com.seat.reservation.common.dto.ReservationDto;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public class Menu implements Persistable<String> {
    @Id
    private String menuId;

    private String menuName;

    @Enumerated(EnumType.STRING)
    private Role role; // 최소 기준 권한

    @Transient
    private boolean isNewFlag = true;

    public MenuDto.search convertMenuDtoShow() {
        return MenuDto.search.builder()
                .menuId(this.menuId)
                .menuName(this.menuName)
                .build();
    }

    public static Menu createMenu(String menuId, String menuName, Role role){
        return Menu.builder()
                .menuId(menuId)
                .menuName(menuName)
                .role(role)
                .isNewFlag(true)
                .build();
    }

    @Override
    public String getId() {
        return this.menuId;
    }

    @Override
    public boolean isNew() {
        return this.isNewFlag;
    }

    @PostLoad
    @PrePersist
    public void setIsNotNewUser(){
        this.isNewFlag = false;
    }
}
