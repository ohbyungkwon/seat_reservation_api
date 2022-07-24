package com.seat.reservation.common.domain;


import com.seat.reservation.common.dto.MenuDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public class MenuAuthority {
    @Id
    @Column(name = "menu_id", nullable = false)
    private String id;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn
    private User user;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public MenuDto.searchAll convertMenuDto(){
        return MenuDto.searchAll.builder()
                .menuId(this.menu.getMenuId())
                .menuName(this.menu.getMenuName())
                .userId(this.user.getUserid())
                .build();
    }
}
