package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.dto.ItemDto;
import com.seat.reservation.common.dto.MerchantDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "merchants")
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Merchant {

    @Id
    @Column(length = 8)
    private Integer merchantRegNum; // 사업자등록번호

    @JoinColumn(nullable = false, name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(nullable = false, name = "upzong_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Upzong upzong;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Item> item = new ArrayList<Item>();// 메뉴 (가맹점 삭제 시 메뉴 전체 삭제를 위해 cascade 사용)

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Review> review = new ArrayList<Review>();// 메뉴 (가맹점 삭제 시 리뷰 전체 삭제를 위해 cascade 사용)

    private String repPhone; // 가맹점 사장님 번호

    private String repName; // 가맹점 사장님 이름

    private String merchantTel; // 가맹점 번호

    private String merchantName; // 가맹점 상호

    @Enumerated(EnumType.STRING)
    private RegisterCode registerCode; // 등록 코드

    private String address; // 가맹점 주소

    private String zipCode; // 가맹점 우편번호

    private LocalTime openTime;

    private LocalTime closeTime;

    private Integer reservationStdHour;

    @CreatedDate
    private LocalDateTime registerDate;  // 가맹점 등록일자

    @LastModifiedDate
    private LocalDateTime modifyDate; // 가맹점 수정일자 -> 상호명 변경 등 변경이력 관리를 위해 사용


    public static Merchant createMerchant(MerchantDto.create merchantDto,
                                              Upzong upzong, User user){
        LocalTime openTime = LocalTime.parse(merchantDto.getOpenTime());
        LocalTime closeTime = LocalTime.parse(merchantDto.getCloseTime());
        return Merchant.builder()
                .merchantRegNum(merchantDto.getMerchantRegNum())
                .repName(merchantDto.getRepName())
                .repPhone(merchantDto.getRepPhone())
                .merchantTel(merchantDto.getMerchantTel())
                .merchantName(merchantDto.getMerchantName())
                .address(merchantDto.getAddress())
                .zipCode(merchantDto.getZipCode())
                .upzong(upzong)
                .user(user)
                .registerCode(RegisterCode.REGISTER)
                .openTime(openTime)
                .closeTime(closeTime)
                .reservationStdHour(merchantDto.getReservationStdHour())
                .build();
    }

    public void updateMerchant(MerchantDto.update merchantDto, Upzong upzong){
        this.repName = merchantDto.getRepName();
        this.repPhone = merchantDto.getRepPhone();
        this.merchantTel = merchantDto.getMerchantTel();
        this.merchantName = merchantDto.getMerchantName();
        this.address = merchantDto.getAddress();
        this.zipCode = merchantDto.getZipCode();
        this.upzong = upzong;
        this.registerCode = merchantDto.getRegisterCode();
        this.openTime = LocalTime.parse(merchantDto.getOpenTime());
        this.closeTime = LocalTime.parse(merchantDto.getCloseTime());
        this.reservationStdHour = merchantDto.getReservationStdHour();
    }

    public void setUpzong(Upzong upzong){
        this.upzong = upzong;
        upzong.getMerchant().add(this);
    }


    public void setRegisterCode(RegisterCode registerCode){
        this.registerCode = registerCode;
    }

    public MerchantDto.showMerchantDetail convertMerchantDetailDto(){
        List<ItemDto.show> itemDtoList = new ArrayList<>();
        for(Item item: this.item) {
            itemDtoList.add(ItemDto.show.builder()
                    .menuName(item.getMenuName())
                    .price(item.getPrice())
                    .build());
        }

        return MerchantDto.showMerchantDetail.builder()
                .merchantRegNum(this.merchantRegNum)
                .repName(this.repName)
                .repPhone(this.repPhone)
                .merchantTel(this.merchantTel)
                .merchantName(this.merchantName)
                .address(this.address)
                .zipCode(this.address)
                .category(this.upzong.getCategory())
                .itemList(itemDtoList)
                .build();
    }
}
