package com.seat.reservation.common.domain;

import com.querydsl.core.group.GroupBy;
import com.seat.reservation.common.domain.enums.RegisterCode;
import com.seat.reservation.common.domain.enums.Role;
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
import java.util.Optional;

@Table
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Merchant {

    @Id
    private Integer merchantRegNum; // 사업자 등록 번호 8자리?

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 자리 등록한 유저 -> long / string ?

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Upzong upzong; // 디테일 조회를 위한 업종 조인

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu; // 디테일 조회를 위한 메뉴 조인

    @Builder.Default
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

    @Enumerated(EnumType.STRING)
    private Role role; // 관리자/사용자

    @CreatedDate
    private LocalDateTime registerDate;  // 가맹점 등록일자

    @LastModifiedDate
    private LocalDateTime modifyDate; // 가맹점 수정일자 -> 상호명 변경 등 변경이력 관리를 위해 사용



    // createMerchant
    public static Merchant createMerchant(Integer merchantRegNum, String repName, String repPhone, String merchantTel, String merchantName, Upzong upzongId, String address, String zipCode) {
        return Merchant.builder()
                .repPhone(repPhone)
                .merchantTel(merchantTel)
                .merchantName(merchantName)
                .address(address)
                .zipCode(zipCode)
                .upzong(upzongId)
                .build();
    }
    // 히스토리 추가

    //TODO {@link User#createUser} {@link User#createUserSimple(String)}참고하여 Merchant 생성

    public static Merchant createMerchantSimple(Integer merchantRegNum){
        return Merchant.builder().merchantRegNum(merchantRegNum).build();
    }

    public static Merchant createMerchantUser(MerchantDto.create merchantDto){
        Role role = Optional.ofNullable(merchantDto.getRole())
                .orElse(Role.UNAUTHORIZATION_ROLE);

        return Merchant.builder()
                .repPhone(merchantDto.getRepPhone())
                .merchantTel(merchantDto.getMerchantTel())
                .merchantName(merchantDto.getMerchantName())
                .address(merchantDto.getAddress())
                .zipCode(merchantDto.getZipCode())
                .address(merchantDto.getAddress())
                .upzong(merchantDto.getUpzongId())
                .role(role)
                .build();
    }


    // create user 참고해서 메서드로 2개 빼기 -> 전역으로 만들어서 다른곳에섣 사용 가능하게
    // merchantDto 처럼 또 선언해서 땡겨갈 수 있도록
    // 업종 코드

    public void setUpzong(Upzong upzong){
        this.upzong = upzong;
        upzong.getMerchant().add(this);
    }


    // Merchant를 만드는 것
    public static Merchant createMerchant(MerchantDto.create dto,
                                             User user, List<Item> itemList){
        return Merchant.builder() // 값을 받을 생성자를 선언한다.
                .merchantRegNum(dto.getMerchantRegNum())
                .user(user)
                .item(itemList)
                .repPhone(dto.getRepPhone())
                .repName(dto.getRepName())
                .merchantTel(dto.getMerchantTel())
                .merchantName(dto.getMerchantName())
                .registerCode(RegisterCode.REGISTER) // enum
                .address(dto.getAddress())
                .zipCode(dto.getZipCode())
                .build();
    }


    public void setRegisterCode(RegisterCode registerCode){
        this.registerCode = registerCode;
    }

    // 가맹점 조회
    public MerchantDto.show selectMerchant(){
        return MerchantDto.show.builder()
                .repName(this.repName)
                .repPhone(this.repPhone)
                .repPhone(this.getRepPhone())
                .merchantTel(this.getMerchantTel())
                .merchantName(this.getMerchantName())
                .address(this.getAddress())
                .zipCode(this.getZipCode())
                .build();
    }

    // 가맹점 디테일 조회
    public MerchantDto.showDetail selectMerchantDetail(){
        return MerchantDto.showDetail.builder()
                .repName(this.repName)
                .repPhone(this.repPhone)
                .repPhone(this.getRepPhone())
                .merchantTel(this.getMerchantTel())
                .merchantName(this.getMerchantName())
                .address(this.getAddress())
                .zipCode(this.getZipCode())
                .upzongId(this.upzong.getId()) // 어떤 업종인지를 가져와야하는데 카테고리?
                .build();
        
    }

    public GroupBy get() {
        return null;
    }
}
