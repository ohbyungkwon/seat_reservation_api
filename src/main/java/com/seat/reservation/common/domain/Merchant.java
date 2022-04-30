package com.seat.reservation.common.domain;

import com.seat.reservation.common.domain.enums.RegisterCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Table
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Merchant {

    @Id
    private int merchantRegNumber; // 사업자 등록 번호 8자리?

    // 사장님 계정 -> 이걸 바탕으로 업종에 연결시켜야 한다.

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User userid; // 자리 등록한 유저 -> long / string ?

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "merchant")
    private List<Item> item; // 메뉴 (가맹점 삭제 시 메뉴 전체 삭제를 위해 cascade 사용)

    private String repPhone; // 가맹점 사장님 번호

    private String repName; // 가맹점 사장님 이름

    private String merChantTel; // 가맹점 번호

    private String merChantName; // 가맹점 상호

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Upzong upzong; // 업종 코드

    @Enumerated(EnumType.STRING)
    private RegisterCode registerCode; // 등록 코드

    private String address; // 가맹점 주소

    private String zipCode; // 가맹점 우편번호

    @CreatedDate
    private LocalDateTime registerDate;  // 가맹점 등록일자

    @LastModifiedDate
    private LocalDateTime modifyDate; // 가맹점 수정일자 -> 상호명 변경 등 변경이력 관리를 위해 사용
    // 히스토리 추가
}
